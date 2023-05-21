# GrpahQL
## Query

http://localhost:8080/graphiql

```
query rentalAgreementDetails {
  rentalAgreementById(id: "rentalAgreement-1") {
    id
    customer {
      id
      name
    }
    car {
      plateNumber
      brand
      price
    }
  }
}
```

Display:
```
{
  "data": {
    "rentalAgreementById": {
      "id": "rentalAgreement-1",
      "customer": {
        "id": "customer-1",
        "name": "Tintin"
      },
      "car": {
        "plateNumber": "AA11BB",
        "brand": "Ferrari"
      }
    }
  }
}
```
