pragma solidity 0.4.24;

import "openzeppelin-solidity/contracts/token/ERC20/MintableToken.sol";

/**
 * @title GameToken
 * @dev Simple ERC20 Token, with mintable and burnable token functionality
 */
contract GameToken is MintableToken {
    string public symbol = "GT";
    string public name = "GameToken";
}
