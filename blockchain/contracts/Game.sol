pragma solidity 0.4.24;

import "./Character.sol";
import "./GameToken.sol";
import "openzeppelin-solidity/contracts/math/SafeMath.sol";

contract Game {
    using SafeMath for uint256;

    enum Status {
        EMPTY,
        FIGHT
    }

    enum MatchStatus {
        WON,
        LOST,
        DRAW
    }

    address public firstCharacter;
    address public secondCharacter;
    address public app;
    address public gameToken;
    address public activeCharacter;
    address public inactiveCharacter;

    uint8 public status;
    uint8 public hitType;
    uint8 public blockType;

    modifier onlyActivePlayer() {
        require(Character(activeCharacter).owner() == msg.sender);
        _;
    }

    modifier onlyApp() {
        require(app == msg.sender);
        _;
    }

    constructor(address _app, address _gameToken)
        public
    {
        require(
            _app != address(0) &&
            _gameToken != address(0)
        );
        app = _app;
        gameToken = _gameToken;
        status = 0;
    }

    function hit(uint8 _hitType, uint8 _blockType) public onlyActivePlayer {
        require(status == uint8(Status.FIGHT));

        if(activeCharacter == firstCharacter){
            hitType = _hitType;
            blockType = _blockType;
            setNext();
        }
        else{
            Character(inactiveCharacter).setBlock(blockType);
            Character(activeCharacter).setBlock(_blockType);

            if(Character(activeCharacter).damage(hitType)){
                if(Character(inactiveCharacter).damage(_hitType)){
                    finishFight(uint8(MatchStatus.DRAW), uint8(MatchStatus.DRAW));
                }
                else{
                    finishFight(uint8(MatchStatus.LOST), uint8(MatchStatus.WON));
                }
            }
            else{
                if(Character(inactiveCharacter).damage(_hitType)){
                    finishFight(uint8(MatchStatus.WON), uint8(MatchStatus.LOST));
                }
                else{
                    setNext();
                }
            }
        }
    }

    function finishFight(uint8 _secondCharacterStatus, uint8 _firstCharacterStatus)
        internal
    {
        status = uint8(Status.EMPTY);
        Character(secondCharacter).reset(_secondCharacterStatus);
        Character(firstCharacter).reset(_firstCharacterStatus);
    }

    function setNext() internal {
        address currentCharacter = activeCharacter;
        activeCharacter = inactiveCharacter;
        inactiveCharacter = currentCharacter;
    }

    function startFight(address _firstCharacter, address _secondCharacter) public onlyApp {
        status = uint8(Status.FIGHT);
        firstCharacter = _firstCharacter;
        secondCharacter = _secondCharacter;
        activeCharacter = _firstCharacter;
        inactiveCharacter = _secondCharacter;
    }

    function resetStatus() public onlyApp {
        status = uint8(Status.EMPTY);
    }
}
