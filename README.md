## Clean Architecture Example (Java)

This repository is a **small but complete demo project** that showcases the **core principles of Clean Architecture** using Java and Spring.

The goal is to model **business rules (domain)** independently from frameworks, databases, and HTTP, and treat those as outer details.

---

## 🎯 Purpose

- Demonstrate the fundamentals of **Clean Architecture**
- Keep the **domain layer framework-independent**
- Put business rules **inside entities** (avoid anemic models)
- Use **use cases** to orchestrate application flow
- Push web/database/framework dependencies to the outer layers

The project is intentionally **small and focused**, optimized for learning and teaching.

---

## 🧱 Architecture Overview

- The **domain** (entities + repository interfaces) sits at the center.
- **Dependency direction always points inward.**
- Entities protect their own invariants and rules.
- Use cases coordinate the **application-level flows** using domain objects.
- Framework and persistence concerns live in **infrastructure** and **adapter** layers.

High-level flow:

- `Controller` → `UseCase` → `Domain`
- `Infrastructure (JPA / InMemory)` → `Domain.Repository` interfaces

---

## 📦 Project Structure (under `src/main/java/com/example/clean_architecture_example`)

```text
adapter/
└── web/
    ├── controller/              (REST controllers)
    └── dto/
        ├── request/             (HTTP request models)
        └── response/            (HTTP response models)

application/
└── usecase/
    ├── order/                   (Order use cases)
    └── product/                 (Product use cases)

config/
└── AppConfig.java               (Use case & repository bean wiring)

domain/
├── entity/
│   ├── Order
│   ├── OrderItem
│   └── Product
├── repository/                  (Repository interfaces)
└── entity/enums/
    └── Status

infrastructure/
└── presistence/                 (Data access layer – JPA & InMemory)
    ├── entity/                  (JPA entities)
    ├── inmemory/                (In-memory repository implementations)
    ├── jpa/adapter/             (Adapters implementing domain repositories)
    ├── mapper/                  (Domain ↔ JPA mappers)
    └── repository/              (Spring Data JPA repositories)
```

> Note: The package name `presistence` is kept as-is to match the current source structure, even though the usual spelling is `persistence`.

---

## 🧠 Domain Layer

### `Product`

`Product` is an independent domain entity responsible for:

- Price validation (`price >= 0`)
- Stock management (`updateStock`, `decreaseStock`)
- Active / inactive state (`activate`, `deactivate`)

All business rules related to product behavior live inside this entity.

### `Order` and `OrderItem`

- `Order` is the main entity controlling the lifecycle and state of an order.
- `OrderItem` stores a **snapshot of product data at the time of ordering**:
  - `productId`, `productName`, `description`, `unitPrice`, `quantity`
  - It does **not** hold a direct reference to `Product`, which keeps historical orders stable even if product data changes later.

The `Status` enum models the order’s status (e.g. created, in progress, etc.).

---

## 🔁 Application Layer (Use Cases)

Use cases represent **application-level flows**.  
They load domain entities through repository interfaces, invoke domain behavior, and persist changes.

### Order Use Cases

- `CreateOrderUseCase`
- `AddProductToOrderUseCase`
- `StartOrderProgressUseCase`

Responsibilities:

- Load required entities from repositories
- Invoke domain methods to apply business rules
- Save updated entities back to repositories

> Core business rules live inside entities; use cases orchestrate them.

### Product Use Cases

- Create product (`CreateProductUseCase`)
- Update price (`UpdateProductPriceUseCase`)
- Update stock (`UpdateProductStockUseCase`)
- Activate / deactivate product (`ActivateProductUseCase`, `DeactivateProductUseCase`)
- Get product details (`GetProductUseCase`)

Each use case is responsible for a single flow and depends only on **repository interfaces**, not concrete infrastructure types.

---

## 🧩 Adapter Layer (Web / Controllers)

Classes under `adapter.web.controller` act as **thin HTTP adapters**:

- Accept request DTOs (`CreateProductRequest`, `UpdateProductPriceRequest`, `AddProductToOrderRequest`, etc.)
- Call exactly one use case per endpoint
- Map the result to response DTOs such as `ProductResponse`
- Contain **no business logic**

This allows the web layer to be replaced (e.g. REST → gRPC) without impacting domain or application logic.

---

## 🧱 Infrastructure Layer

The `infrastructure.presistence` package contains data access concerns:

- `entity` → JPA entity classes (`ProductJpaEntity`, `OrderJpaEntity`, `OrderItemJpaEntity`)
- `repository` → Spring Data JPA repository interfaces
- `mapper` → Converters between domain and JPA entities (`ProductMapper`, `OrderMapper`, `OrderItemMapper`)
- `jpa.adapter` → Adapters implementing domain repository interfaces (`JpaProductRepositoryAdapter`, `JpaOrderRepositoryAdapter`, `JpaOrderItemAdapter`)
- `inmemory` → Simple in-memory repository implementations

This layer depends on domain **only through interfaces**; the domain never depends on JPA or Spring Data.

---

## 🧪 Testing Ideas (Optional)

The current design supports clear separation for tests:

- **Domain unit tests**: For `Product`, `Order`, and `OrderItem` behavior.
- **Use case tests**: Mock repositories and test flows like `CreateProductUseCase`, `AddProductToOrderUseCase`, etc.
- **Web layer tests**: `@WebMvcTest` for controller request/response behavior.

These are not strictly required, but they fit naturally with this architecture.

---

## ✅ Key Idea

> Business rules come first (domain).  
> Use cases coordinate them.  
> Frameworks and infrastructure stay at the outermost layer.

This repository is primarily for educational purposes; feel free to use, modify, and adapt it to your needs.
