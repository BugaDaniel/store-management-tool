#!/bin/bash

curl --location --request PUT \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW5QYXNz" \
  -d '{
    "id": 1,
    "name": "firstUpdate",
    "price": 24.22,
    "quantity": 4,
    "description": "saddescr",
    "category": "aCateg",
    "brand": "somBrand"
  }' \
  'http://localhost:8086/api/products/v1/1'&

  curl --location --request PUT \
    -H "Content-Type: application/json" \
    -H "Authorization: Basic YWRtaW46YWRtaW5QYXNz" \
    -d '{
      "id": 1,
      "name": "secondUpdate",
      "price": 24.22,
      "quantity": 4,
      "description": "saddescr",
      "category": "aCateg",
      "brand": "somBrand"
    }' \
    'http://localhost:8086/api/products/v1/1'