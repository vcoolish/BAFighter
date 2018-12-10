const App = artifacts.require("contracts/App.sol");
const GameToken = artifacts.require("contracts/GameToken.sol");
const Character = artifacts.require("contracts/Character.sol");
const Game = artifacts.require("contracts/Game.sol");

contract('Fight', function (accounts) {
    
    var appOwner = accounts[0];
    var firstCharacterOwner = accounts[1];
    var secondCharacterOwner = accounts[2];
    var unknownAcc = accounts[3];

    let instanceOfApp;
    let instanceOfGame;
    let instanceOfFirstCharacter;
    let instanceOfSecondCharacter;
    let instanceOfGameToken;

    let appAddress;
    let gameAddresses;
    let characterAddresses;
    let tokenAddress;

    let comment = "London is the capital of GB";
    let amount = 100;

    let HEAD = 0;
    let BODY = 1;
    let FEET = 2;

    beforeEach("init", async () => {

    instanceOfApp = await App.new({from: appOwner});
    instanceOfGameToken = await web3.eth.contract(GameToken.abi).at(await instanceOfApp.gameToken());

    appAddress = instanceOfApp.address;
    tokenAddress = instanceOfGameToken.address;

    await instanceOfApp.tokenReward(amount, firstCharacterOwner, {from: appOwner});
    await instanceOfApp.tokenReward(amount, secondCharacterOwner, {from: appOwner});

    await instanceOfApp.createCharacter({from: firstCharacterOwner});
    await instanceOfApp.createCharacter({from: secondCharacterOwner});

    characterAddresses = await instanceOfApp.getCharacters({from: appOwner});

    instanceOfFirstCharacter = await web3.eth.contract(Character.abi).at(characterAddresses[0]);
    instanceOfSecondCharacter = await web3.eth.contract(Character.abi).at(characterAddresses[1]);

    await instanceOfApp.startGame(characterAddresses[0], characterAddresses[1], 0, {from: firstCharacterOwner});

    gameAddresses = await instanceOfApp.getGames();
    instanceOfGame = await web3.eth.contract(Game.abi).at(gameAddresses[0]);

    assert.equal(instanceOfGame.address, instanceOfFirstCharacter.game(), "game address set");
    });

    it("tests game process", async () => {
    await instanceOfGame.hit(HEAD, FEET, {from: firstCharacterOwner, gas: 110000});
    await instanceOfGame.hit(BODY, HEAD, {from: secondCharacterOwner});
    await instanceOfGame.hit(FEET, BODY, {from: firstCharacterOwner});
    await instanceOfGame.hit(HEAD, FEET, {from: secondCharacterOwner});
    await instanceOfGame.hit(HEAD, FEET, {from: firstCharacterOwner});
    await instanceOfGame.hit(BODY, HEAD, {from: secondCharacterOwner});
    await instanceOfGame.hit(FEET, BODY, {from: firstCharacterOwner});
    await instanceOfGame.hit(HEAD, FEET, {from: secondCharacterOwner});
    await instanceOfGame.hit(FEET, BODY, {from: firstCharacterOwner});
    await instanceOfGame.hit(HEAD, FEET, {from: secondCharacterOwner, gas: 110000});

    await instanceOfApp.startGame(characterAddresses[1], characterAddresses[0], 0, {from: secondCharacterOwner});

    await instanceOfGame.hit(HEAD, FEET, {from: secondCharacterOwner});
    await instanceOfGame.hit(BODY, HEAD, {from: firstCharacterOwner});
    await instanceOfGame.hit(FEET, BODY, {from: secondCharacterOwner});
    await instanceOfGame.hit(HEAD, FEET, {from: firstCharacterOwner});
    await instanceOfGame.hit(HEAD, FEET, {from: secondCharacterOwner});
    await instanceOfGame.hit(BODY, HEAD, {from: firstCharacterOwner});
    await instanceOfGame.hit(FEET, BODY, {from: secondCharacterOwner});
    await instanceOfGame.hit(HEAD, FEET, {from: firstCharacterOwner});
    await instanceOfGame.hit(FEET, BODY, {from: secondCharacterOwner});
    await instanceOfGame.hit(HEAD, FEET, {from: firstCharacterOwner, gas: 120000});

    assert.equal(await instanceOfGame.status().toNumber(), 0, "emptied");
    assert.equal(await instanceOfFirstCharacter.reputation().toNumber(), 1, "rep up");
    assert.equal(await instanceOfSecondCharacter.reputation().toNumber(), 0, "rep lost");
    assert.equal(await instanceOfFirstCharacter.health().toNumber(), 100, "health update");
    });
});