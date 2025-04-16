# Semantic Kernel Plugins

## Case#1 - The currency is correctly provided in the request
```json
POST http://localhost:9092/v1/currency/chat/messages
Content-Type: application/json

{
  "message": "Convert 10 EUR to USD."
}
```
model response is using plugin using the predined exchange rate in the code. <br/>
Please find the predefined exchange rate in the com.epam.training.gen.ai.plugin.service.CurrencyConverterService
```json
HTTP/1.1 200
vary: accept-encoding
Content-Type: application/json
Date: Wed, 16 Apr 2025 20:02:35 GMT

[
  {
    "authorRole": "assistant",
    "content": "10 EUR is approximately 11.40 USD.",
    "contentType": "text",
    "modelId": ""
  }
]
```

## Case#2 - Ask model to provide a list of available currencies
```json
POST http://localhost:9092/v1/currency/chat/messages
Content-Type: application/json

{
  "message": "Please provide currency list"
}
```
model response is using plugin
```json
[
  {
    "authorRole": "assistant",
    "content": "Here are some of the available currencies:\n- Belarusian Ruble\n- Euro\n- USD (United States Dollar)\n- Japanese Yen\n- Swiss Franc\n- Swedish Krona\n\nLet me know if you need information about more currencies or any other assistance!",
    "contentType": "text",
    "modelId": ""
  }
]
```
## Case#3 - The currency is Not correctly provided in the request
```json
POST http://localhost:9092/v1/currency/chat/messages
Content-Type: application/json

{
  "message": "Convert 10 ASD to USD."
}
```
model response is using plugin
```json
[
  {
    "authorRole": "assistant",
    "content": "It seems \"ASD\" is not a recognized currency. Could you please confirm the correct currency code? If you need help, I can provide a list of valid currencies.",
    "contentType": "text",
    "modelId": ""
  }
]
```
## Case#4 - The example response of the model in case amount is not defined in the request
```json
POST http://localhost:9092/v1/currency/chat/messages
Content-Type: application/json

{
  "message": "Convert from EUR to USD."
}
```
model response is using plugin
```json
[
  {
    "authorRole": "assistant",
    "content": "Could you please specify the amount you wish to convert from EUR to USD?",
    "contentType": "text",
    "modelId": ""
  }
]
```
## Case#5 - The provided currency is Euro and model recognizes that EUR should be used
```json
POST http://localhost:9092/v1/currency/chat/messages
Content-Type: application/json

{
  "message": "Convert from Euro to USD."
}
```
model response is using plugin
```json
[
  {
    "authorRole": "assistant",
    "content": "Please provide the amount you would like to convert from Euro to USD.",
    "contentType": "text",
    "modelId": ""
  }
]
```
