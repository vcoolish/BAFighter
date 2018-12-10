pragma solidity 0.4.24;

import "openzeppelin-solidity/contracts/math/SafeMath.sol";
import "./GameToken.sol";

contract Character {
    using SafeMath for uint256;

    enum Block {
        TOP,
        MIDDLE,
        LOW
    }

    enum MatchStatus {
        WON,
        LOST,
        DRAW
    }

    uint256 public health;
    uint256 public damage;
    uint256 public reputation;
    uint8 public blocked;

    address public game;
    address public app;
    address public owner;
    address public gameToken;

    event MatchResult(uint indexed _matchStatus);

    modifier onlyGame() {
        require(msg.sender == game);
        _;
    }

    modifier onlyApp() {
        require(msg.sender == app);
        _;
    }

    constructor(
        uint256 _health,
        address _app,
        address _owner,
        address _gameToken
    )
        public
    {
        require(
            _health > 0 &&
            _owner != address(0) &&
            _app != address(0) &&
            _gameToken != address(0)
        );
        damage = _health/5;
        health = _health;
        app = _app;
        owner = _owner;
        gameToken = _gameToken;
    }

    function setGame(address _game) public onlyApp returns(bool) {
        require(_game != address(0));

        game = _game;
        return true;
    }

    function setBlock(uint8 _type) public onlyGame {
        blocked = _type;
    }

    function damage(uint8 _type) public onlyGame returns(bool) {
        if(health > damage) {
            if(blocked != _type){
                health = health - damage;
            }
            return false;
        }
        else{
            return true;
        }
    }

    function reset(uint8 _result) public onlyGame {
        if(_result == uint8(MatchStatus.WON)){
            reputation = reputation.add(1);
        }
        else if(_result == uint8(MatchStatus.LOST) && reputation > 0) {
            reputation = reputation - 1;
        }

        health = GameToken(gameToken).balanceOf(owner);
        game = address(0);

        emit MatchResult(_result);
    }
}
