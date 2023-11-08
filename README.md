
# Crypto Trading System Implementation

## Overview
This document outlines the implementation details of a crypto trading system developed with the Spring Boot framework utilizing an in-memory H2 database.

## Table Structures
- **Users**: Stores user credentials and wallet balance.
- **Transactions**: Records all buy/sell transactions.
- **Crypto_Prices**: Contains the latest aggregated prices from Binance and Huobi.

## Functionalities

### Trading Pairs
- Ethereum (ETH/USDT)
- Bitcoin (BTC/USDT)

### Features
1. **Spring Profiles:**
    -  Utilize Spring Profiles to separate configuration for different environments (dev, test, prod).
2. **Validation**
    - Apply input validation using @Valid and Hibernate Validator annotations in  DTOs to ensure the data integrity of the requests.
3. **Database Optimizations**
    - Use JPA/Hibernate optimizations such as lazy loading, batch fetching, and query tuning to enhance performance with the in-memory database.
4. **Transaction Management**
    - Trading transactions are wrapped in database transactions to maintain data integrity.
5. **Idempotency in Trading**
    - Ensure that trade requests are idempotent to avoid duplicate trades. It's achieved by using a unique identifier for each trade request.
6. **Data Transfer Efficiency**
    - When calling external APIs, request only the data service need and process it efficiently to reduce memory and CPU overhead.
7. **Layered Architecture**
    - Structure this service using the layered architecture (Controller, Service, Repository) to decouple the code and facilitate testing.
8. **Error Handling**
    - Apply global exception handling mechanism with @ControllerAdvice to handle exceptions and return proper HTTP responses.
## Assumptions
- Users are pre-authenticated and authorized for API access.
- Each user starts with a wallet balance of 50,000 USDT.
- The system exclusively supports Ethereum and Bitcoin trading pairs.

## Implemented Tasks

1. **Price Aggregation Scheduler**
    - A scheduler running at 10-second intervals fetches and stores the best pricing from Binance and Huobi.

2. **Aggregated Price API**
    - An API endpoint has been created to provide the latest best aggregated price.

3. **Trading API**
    - A trading API endpoint is available for users to place trades at the latest aggregated prices.

4. **Wallet Balance API**
    - Users can retrieve their current wallet balances through a dedicated API endpoint.

5. **Trading History API**
    - Users can retrieve their last 10 transactions.

## API Endpoints

### Get Wallet Balances
Retrieves the wallet balance information for a specific user.

- **URL**: `/users/{userId}/wallets/balances`
- **Method**: `GET`
#### Query Parameters

| Parameter | Type    | Description                                 | Required |
|-----------|---------|---------------------------------------------|----------|
| userId      | String  | The GUID of the user.    | Yes      |
- **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
 ```json
{
    "id": 2,
    "balance": 50000,
    "type": "BTCUSDT",
    "timestamp": "2023-11-09T02:58:15.297199"
}
 ```

### Get Transactions
Provides a list of transactions for a specific user.

- **URL**: `/users/{userId}/transactions`
- **Method**: `GET`
#### Query Parameters

| Parameter | Type    | Description                                 | Required |
|-----------|---------|---------------------------------------------|----------|
| userId      | String  | The GUID of the user.    | Yes      |
- **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
```json
[
{
    "id": 2,
    "type": "BTCUSDT",
    "amount": 10,
    "timestamp": "2023-11-09T02:56:06.848447"
},
{
    "id": 1,
    "type": "BTCUSDT",
    "amount": 1,
    "timestamp": "2023-11-09T02:56:06.843446"
}
]
```

_Note: Integration with third-party systems is not included._
### Execute Trade
Allows a user to execute a trade with the provided request details.

- **URL**: `/users/trade`
- **Method**: `POST`
- **Request Body**:

| Parameter | Type    | Description                                 | Required |
|-----------|---------|---------------------------------------------|----------|
| id      | String  | The ID of the trade request.   | Yes      |
| userId      | String  | The GUID of the user.    | Yes      |
| type      | String  | The cryptocurrency type, e.g., `ETH` or `BTC`.   | Yes      |
| quantity      | double  | The amount of cryptocurrency to trade.   | Yes      |
| tradeType      | String  | The type of trade, e.g., `BUY` or `SELL`.    | Yes      |

- **Data Example**:
```json
{
    "id": "23", 
    "userId": "1187c72c-0c88-4c06-b6db-66b5a15ff3fa",
    "type": "BTC",
    "quantity": 1.5,
    "tradeType": "BUY"
}
```
- **Success Response**:
  - **Code**: `200 OK`
- **Content**:
```json
{
    "id": 5,
    "type": "BTCUSDT",
    "amount": 35644.73000000001,
    "timestamp": "2023-11-09T03:00:51.142025"
}
```
### Get Latest Crypto Price

Retrieves the latest price for a specified cryptocurrency.

- **URL**: `/prices/latest`


#### Query Parameters

| Parameter | Type    | Description                                 | Required |
|-----------|---------|---------------------------------------------|----------|
| type      | String  | The type of cryptocurrency (ETH or BTC).    | Yes      |

#### Request Example

```bash
curl -X GET "http://localhost:8080/prices/latest?type=BITCOIN" -H "accept: application/json"
```
- **Success Response**:
- **Code**: `200 OK`
- **Content**:
```json
{
  "id": 3,
  "symbol": "BTCUSDT",
  "bidPrice": 35754.99,
  "askPrice": 35753.78,
  "timestamp": "2023-11-09T03:10:33.434201"
}
```
_Note: The source code includes mock data constructors designed for API testing, but these may be skipped if not interest in._
## Remarks
Test case and optimization will be added later because the deadline of this exercise is only 2 days.

