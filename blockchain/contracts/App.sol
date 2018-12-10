pragma solidity 0.4.24;

import "openzeppelin-solidity/contracts/math/SafeMath.sol";
import "openzeppelin-solidity/contracts/ownership/Ownable.sol";
import "./GameToken.sol";
import "./Game.sol";
import "./Character.sol";

contract App is Ownable {
    using SafeMath for uint256;

    address public gameToken;

    address[] public games;
    address[] public characters;

    mapping(address => bool) public characterStatus;
    mapping(address => address) public characterByUser;

    event CharacterCreated(address indexed _character);
    event GameStarted(address indexed _game);

    constructor() public {
        gameToken = new GameToken();
        games.push(new Game(address(this), gameToken));
    }

    function tokenReward(uint256 _amount, address _receiver) public onlyOwner {
        GameToken(gameToken).mint(_receiver, _amount);
    }

    function createCharacter() public returns(address){
        address character = new Character(
            GameToken(gameToken).balanceOf(msg.sender),
            address(this),
            msg.sender,
            gameToken
        );
        require(
            character != address(0) &&
            characterByUser[msg.sender] == address(0)
        );

        characters.push(character);
        characterStatus[character] = true;
        characterByUser[msg.sender] = character;
        emit CharacterCreated(character);

        return character;
    }

    function startGame(
        address _firstCharacter,
        address _secondCharacter,
        uint8 _gameNumber
    )
        public
        onlyOwner
    {
        require(
            _firstCharacter != _secondCharacter &&
            _firstCharacter != address(0) &&
            _secondCharacter != address(0) &&
            games.length >= _gameNumber &&
            characterStatus[_firstCharacter] &&
            characterStatus[_secondCharacter] &&
            Game(games[_gameNumber]).status() != 1
        );

        Game(games[_gameNumber]).startFight(_firstCharacter, _secondCharacter);


        Character(_firstCharacter).setGame(games[_gameNumber]);
        Character(_secondCharacter).setGame(games[_gameNumber]);

        emit GameStarted(games[_gameNumber]);
    }

    function removeCharacter(address _character) internal {
        uint _count = characters.length;
        for (uint256 i = 0; i < _count; i = i.add(1)) {
            if (characters[i] == _character) {

                characterStatus[_character] = false;
                delete characters[i];

                characters[i] = characters[_count.sub(1)];

                characters.length = characters.length.sub(1);
                return;
            }
        }
    }

    function characterOf(address _user) public view returns (address) {
        return characterByUser[_user];
    }

    function getCharacters() public view returns (address[]) {
        return characters;
    }

    function getGames() public view returns (address[]) {
        return games;
    }

    function resetGame(address _game) public onlyOwner {
        Game(_game).resetStatus();
    }

    function addGame() public onlyOwner {
        games.push(new Game(address(this), gameToken));
    }
}