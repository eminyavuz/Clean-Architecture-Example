# Clean Architecture Example (Java)

This repository is a **small but complete demo project** created to demonstrate the **core principles of Clean Architecture** using Java.

The project follows a **domain-first design approach**, where business rules are modeled independently of frameworks, databases, and delivery mechanisms.

> The goal is clarity, not complexity.

---

## 🎯 Purpose

- Demonstrate Clean Architecture fundamentals
- Keep the **domain layer framework-independent**
- Place business rules inside entities
- Use **use cases** to orchestrate application flow
- Avoid anemic models and framework-driven design

This project is intentionally **small and focused**, serving as a reference implementation.

---

## 🧱 Architecture Overview

- Domain is the core of the system
- Dependencies always point inward
- Entities protect their own invariants
- Use cases coordinate business flows
- Frameworks are treated as external details

Controller → UseCase → Domain
Infrastructure → Domain

---

## 📦 Project Structure

```text
adapter/
└── web/
    ├── controller/          (REST controllers)
    ├── dto/
    │   ├── request/
    │   └── response/
    └── mapper/
application/
└── usecase/
    ├── order/
    └── product/
domain/
├── entity/
│   ├── Order
│   ├── OrderItem
│   └── Product
├── repository/              (Repository interfaces)
└── enums/
    └── Status
infrastructure/
└── persistence/             (Repository implementations – optional)

```

---

## 🧠 Domain Design Notes

### Order
- Central business entity that controls order behavior
- Responsible for:
    - Status transitions
    - Managing `OrderItem` collection
    - Calculating total price
- Prevents invalid operations (e.g. adding items to shipped orders)

> While not a full DDD aggregate implementation, `Order` acts as the main consistency boundary.

---

### OrderItem
- Represents a **snapshot of product data at order time**
- Stores:
    - `productId`
    - `productName`
    - `description`
    - `unitPrice`
    - `quantity`
- Does **not** reference `Product` directly

This ensures historical orders remain consistent even if product data changes later.

---

### Product
- Independent domain entity
- Responsible for:
    - Price validation
    - Stock management
    - Active / inactive state

All business rules related to product behavior live inside the entity.

---

## 🔁 Application Layer (Use Cases)

Use cases represent **application-level business flows**.

### Order Use Cases
- `CreateOrderUseCase`
- `AddProductToOrderUseCase`
- `StartOrderProgressUseCase`

Responsibilities:
- Load entities from repositories
- Coordinate domain interactions
- Persist changes
- Enforce application flow rules

> Use cases do **not** contain business rules — entities do.

---

### Product Use Cases
- Product creation
- Price updates
- Stock management
- Activation / deactivation
- Product retrieval

---

## 🧩 Adapter Layer (Controllers)

Controllers act as **thin adapters**:

- Accept request DTOs
- Call exactly one use case
- Return response DTOs
- Contain no business logic

This layer exists only to adapt external input (HTTP) to internal use cases.

---

## 🧪 Validation Strategy

| Type | Location |
|-----|---------|
| Basic input validation | Request DTOs |
| Business rules | Domain entities |
| Flow & orchestration | Use cases |

---

## ✅ Key Idea

> Business rules come first.  
> Use cases coordinate them.  
> Frameworks come last.

---

## 📄 License

Educational purpose only.  
Feel free to use, modify, and adapt.
