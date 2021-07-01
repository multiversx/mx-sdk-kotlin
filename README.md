
## [CHANGELOG](CHANGELOG.md)

## Elrond Kotlin SDK

This is the kotlin implementation of Elrond SDK

This project was primarily designed for Android but is also compatible with any Kotlin-friendly app since it doesn't use the Android SDK

## Usage
This SDK is built with the clean architecture principles.  
Interaction are done through usecases

Here is an example for sending a transaction.
```
// Create a wallet from mnemonics
val wallet = Wallet.createFromMnemonic(..., 0)

// Get information related to this address (ie: balance and nonce)
val account = ErdSdk.getAccountUsecase().execute(Address.fromHex(wallet.publicKeyHex))

// Get the network informations
val networkConfig = ErdSdk.getNetworkConfigUsecase().execute()

// Create the transaction object
val transaction = Transaction(
    sender = account.address,
    receiver = Address.fromHex(...),
    value = 1000000000000000000.toBigInteger(), // 1 xEGLD
    data = "Elrond rocks !",
    chainID = networkConfig.chainID,
    gasPrice = networkConfig.minGasPrice,
    gasLimit = networkConfig.minGasLimit,
    nonce = account.nonce
)

// Send transaction.
// Signature is handled internally
val sentTransaction = ErdSdk.sendTransactionUsecase().execute(transaction, wallet)
Log.d("Transaction", "tx:${sentTransaction.txHash}")
```

In a real world example, the usescases would be injected  
The sample application showcase how to do it on Android with Hilt framework (see the [Sample App](#sample-app)).

## Usecases list

##### API
| Usecase  | Endpoint  |
| ------------- | ------------- |
| GetAccountUsecase  |  [GET address/:bech32Address](https://docs.elrond.com/sdk-and-tools/rest-api/addresses/#get-address) |
| GetAddressBalanceUsecase  | [GET address/:bech32Address/balance](https://docs.elrond.com/sdk-and-tools/rest-api/addresses/#get-address-balance) |
| GetAddressNonceUsecase  | [GET address/:bech32Address/nonce](https://docs.elrond.com/sdk-and-tools/rest-api/addresses/#get-address-nonce) |
| GetAddressTransactionsUsecase  | [GET address/:bech32Address/transactions](https://docs.elrond.com/sdk-and-tools/rest-api/addresses/#get-address-transactions) |
| GetTransactionInfoUsecase  | [GET transaction/:txHash](https://docs.elrond.com/sdk-and-tools/rest-api/transactions/#get-transaction) |
| GetTransactionStatusUsecase  | [GET transaction/:txHash/status](https://docs.elrond.com/sdk-and-tools/rest-api/transactions/#get-transaction-status) |
| SendTransactionUsecase  | [POST transaction/send](https://docs.elrond.com/sdk-and-tools/rest-api/transactions/#send-transaction) |
| EstimateCostOfTransactionUsecase  | [POST transaction/cost](https://docs.elrond.com/sdk-and-tools/rest-api/transactions/#estimate-cost-of-transaction) |
| GetNetworkConfigUsecase  | [GET network/config](https://docs.elrond.com/sdk-and-tools/rest-api/network/#get-network-configuration) |
| QueryContractUsecase  | [POST vm-values/query](https://docs.elrond.com/sdk-and-tools/rest-api/virtual-machine/#compute-output-of-pure-function) |
| QueryContractHexUsecase  | [POST vm-values/hex](https://docs.elrond.com/sdk-and-tools/rest-api/virtual-machine/#compute-hex-output-of-pure-function) |
| QueryContractStringUsecase  | [POST vm-values/string](https://docs.elrond.com/sdk-and-tools/rest-api/virtual-machine/#compute-string-output-of-pure-function) |
| QueryContractIntUsecase  | [POST vm-values/int](https://docs.elrond.com/sdk-and-tools/rest-api/virtual-machine/#get-integer-output-of-pure-function) |

#### ESDT
| Usecase  | Description  |
| ------------- | ------------- |
| GetAllEsdtUsecase  | Get all ESDT tokens for an address: [GET address/:bech32Address/esdt](https://docs.elrond.com/developers/esdt-tokens/#get-all-esdt-tokens-for-an-address) |
| GetEsdtBalanceUsecase  | Get balance for an address and an ESDT token: [GET address/:bech32Address/esdt/:tokenIdentifier](https://docs.elrond.com/developers/esdt-tokens/#get-balance-for-an-address-and-an-esdt-token) |
| GetAllIssuedEsdtUsecase  | Get all issued ESDT tokens: [GET network/esdts](https://docs.elrond.com/developers/esdt-tokens/#get-all-issued-esdt-tokens) |
| GetEsdtPropertiesUsecase  | Get ESDT token properties: [POST vm-values/query](https://docs.elrond.com/developers/esdt-tokens/#get-esdt-token-properties) |
| GetEsdtSpecialRolesUsecase  | Get special roles for a token: [POST vm-values/query](https://docs.elrond.com/developers/esdt-tokens/#get-special-roles-for-a-token) |
| IssueEsdtUsecase  | Issue fungible ESDT tokens [(doc)](https://docs.elrond.com/developers/esdt-tokens/#issuance-of-fungible-esdt-tokens) |
| TransferEsdtUsecase  | Transfert ESDT to any account [(doc)](https://docs.elrond.com/developers/esdt-tokens/#transfers) |
| MintEsdtUsecase  | Mint ESDT [(doc)](https://docs.elrond.com/developers/esdt-tokens/#minting) |
| BurnEsdtUsecase  | Burn ESDT [(doc)](https://docs.elrond.com/developers/esdt-tokens/#burning) |
| PauseAccountEsdtUsecase  | Pause/UnPause ESDT [(doc)](https://docs.elrond.com/developers/esdt-tokens/#pausing-and-unpausing) |
| FreezeAccountEsdtUsecase  | Freeze/UnFreeze ESDT [(doc)](https://docs.elrond.com/developers/esdt-tokens/#freezing-and-unfreezing) |
| WipeAccountEsdtUsecase  | Wipe ESDT [(doc)](https://docs.elrond.com/developers/esdt-tokens/#wiping) |
| SetSpecialRolesEsdtUsecase  | Set/Unset Special roles [(doc)](https://docs.elrond.com/developers/esdt-tokens/#setting-and-unsetting-special-roles) |
| ChangeOwnerEsdtUsecase  | Transfer token management rights [(doc)](https://docs.elrond.com/developers/esdt-tokens/#transferring-token-management-rights) |
| UpgradeEsdtUsecase  | Change properties [(doc)](https://docs.elrond.com/developers/esdt-tokens/#upgrading-changing-properties) |


#### Contract
| Usecase  | Description  |
| ------------- | ------------- |
| CallContractUsecase  | Interact with a Smart Contract (execute function): equivalent to [`erdpy contract call`](https://docs.elrond.com/sdk-and-tools/erdpy/erdpy/) |

##### DNS
| Usecase  | Description  |
| ------------- | ------------- |
| RegisterDnsUsecase  | Send a register transaction to the appropriate DNS contract from given user and with given name: equivalent to [`erdpy dns register`](https://docs.elrond.com/sdk-and-tools/erdpy/erdpy/) |
| GetDnsRegistrationCostUsecase  | Gets the registration cost from a DNS smart contract: equivalent to [`erdpy dns registration-cost`](https://docs.elrond.com/sdk-and-tools/erdpy/erdpy/) |
| CheckUsernameUsecase  | Can be useful for validating a text field before calling `RegisterDnsUsecase `|


## Configuration
```
// default value is ProviderUrl.DevNet
ErdSdk.setNetwork(ProviderUrl.MainNet)

// configure the OkHttpClient
ErdSdk.elrondHttpClientBuilder.apply {
    addInterceptor(HttpLoggingInterceptor())
}
```

## Build
The SDK is not yet uploaded to a maven repository  
You can build the jar from the sources by running `mvn package`

## Sample App
For a complete example you can checkout this [sample application](https://github.com/Alexandre-saddour/ElrondKotlinSampleApp)
