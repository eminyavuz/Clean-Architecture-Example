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
└── persistence/                 (Data access layer – JPA & InMemory)
    ├── entity/                  (JPA entities)
    ├── inmemory/                (In-memory repository implementations)
    ├── jpa/adapter/             (Adapters implementing domain repositories)
    ├── mapper/                  (Domain ↔ JPA mappers)
    └── repository/              (Spring Data JPA repositories)
```

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
- List products (`ListProductsUseCase`)
- Get product details (`GetProductUseCase`)
- Update price (`UpdateProductPriceUseCase`)
- Update stock (`UpdateProductStockUseCase`)
- Activate / deactivate product (`ActivateProductUseCase`, `DeactivateProductUseCase`)

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

The `infrastructure.persistence` package contains data access concerns:

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

---

## API Test Console (local UI)

After starting the application, open:

**http://localhost:8080/**

A superadmin-style panel lets you manage products (list, create, activate/deactivate), orders, and inspect API responses. Product list uses `GET /products`.

---

## Postman / API testing guide

**Base URL:** `http://localhost:8080`

**Headers (for requests with a body):**

```http
Content-Type: application/json
Accept: application/json
```

**Postman tip:** Create environment variables `baseUrl`, `productId`, and `orderId`. After step 1, save `id` from the response into `productId`; after step 4, save the order id into `orderId`. Use `{{baseUrl}}/products/{{productId}}` in later requests.

---

### Recommended happy-path flow

Run the steps **in order**. Each step builds on the previous one.

```text
1. Create product     → save productId
2. List products      → verify product appears
3. Create order       → save orderId
4. Add item to order  → stock decreases on product
5. Start order        → order status CREATED → ON_PROGRESS
```

---

### Step 1 — Create product

| | |
|---|---|
| **Method** | `POST` |
| **URL** | `{{baseUrl}}/products/create` |

**Body (raw JSON):**

```json
{
  "productName": "Wireless Mouse",
  "description": "Ergonomic wireless mouse for demo orders",
  "price": 29.99,
  "stock": 50,
  "isActive": true
}
```

**Success:** `200 OK`

**Response example:**

```json
{
  "id": 1,
  "productName": "Wireless Mouse",
  "description": "Ergonomic wireless mouse for demo orders",
  "price": 29.99,
  "stock": 50,
  "active": true
}
```

> Save `id` as `productId` for the next steps.

---

### Step 2 — List all products

| | |
|---|---|
| **Method** | `GET` |
| **URL** | `{{baseUrl}}/products` |
| **Body** | none |

**Success:** `200 OK`

**Response example:**

```json
[
  {
    "id": 1,
    "productName": "Wireless Mouse",
    "description": "Ergonomic wireless mouse for demo orders",
    "price": 29.99,
    "stock": 50,
    "active": true
  }
]
```

---

### Step 3 — Get product by ID (optional)

| | |
|---|---|
| **Method** | `GET` |
| **URL** | `{{baseUrl}}/products/{{productId}}` |
| **Body** | none |

**Success:** `200 OK` — same shape as a single object in step 1.

**Error example** (`404` if product not found — when `ProductNotFoundException` is thrown):

```json
{
  "code": "PRODUCT_NOT_FOUND",
  "message": "Product not found with id: 999",
  "timeStamp": "2026-05-31T12:00:00Z",
  "path": "/products/999"
}
```

---

### Step 4 — Create order

| | |
|---|---|
| **Method** | `POST` |
| **URL** | `{{baseUrl}}/orders/create` |
| **Body** | none |

**Success:** `200 OK`

**Response example:**

```json
1
```

> The body is a plain number (order id). Save it as `orderId`.

---

### Step 5 — Add product to order

| | |
|---|---|
| **Method** | `POST` |
| **URL** | `{{baseUrl}}/orders/{{orderId}}/items` |

**Body (raw JSON):**

```json
{
  "productId": 1,
  "quantity": 2
}
```

Use the `productId` from step 1 and `orderId` from step 4.

**Success:** `200 OK` — empty body.

**Side effect:** Product stock is reduced by `quantity` (e.g. stock `50` → `48`).

**Error examples:**

Validation (`400`):

```json
{
  "code": "VALIDATION_ERROR",
  "message": "Order Not Found",
  "timeStamp": "2026-05-31T12:00:00Z",
  "path": "/orders/999/items"
}
```

Not enough stock (`409` — when `NotEnoughStockException` is thrown):

```json
{
  "code": "NOT_ENOUGH_STOCK",
  "message": "Not enough stock for product id: 1",
  "timeStamp": "2026-05-31T12:00:00Z",
  "path": "/orders/1/items"
}
```

---

### Step 6 — Start order progress

| | |
|---|---|
| **Method** | `POST` |
| **URL** | `{{baseUrl}}/orders/{{orderId}}/start` |
| **Body** | none |

**Success:** `200 OK` — empty body.

**Business rule:** Order must be in `CREATED` status. If already `ON_PROGRESS`, `SHIPPED`, or `CANCELLED`, you get `400`:

```json
{
  "code": "VALIDATION_ERROR",
  "message": "Order must be on 'Created' status",
  "timeStamp": "2026-05-31T12:00:00Z",
  "path": "/orders/1/start"
}
```

---

### Optional — Product maintenance (any order)

#### Update price

| | |
|---|---|
| **Method** | `PUT` |
| **URL** | `{{baseUrl}}/products/{{productId}}/price` |

```json
{
  "newPrice": 24.99
}
```

**Success:** `200 OK` — empty body. Run **GET** `/products/{{productId}}` to confirm.

---

#### Update stock

| | |
|---|---|
| **Method** | `PUT` |
| **URL** | `{{baseUrl}}/products/{{productId}}/stock` |

```json
{
  "newStock": 100
}
```

**Success:** `200 OK` — empty body.

---

#### Activate / deactivate product

| | |
|---|---|
| **Activate** | `PUT` `{{baseUrl}}/products/{{productId}}/activate` |
| **Deactivate** | `PUT` `{{baseUrl}}/products/{{productId}}/deactivate` |

No body. **Success:** `200 OK` — empty body.

> Adding an **inactive** product to an order may fail depending on use-case rules.

---

### Error response shape (all handled errors)

Most errors return:

```json
{
  "code": "VALIDATION_ERROR",
  "message": "Human-readable message",
  "timeStamp": "2026-05-31T12:00:00.123456789Z",
  "path": "/products/1/price"
}
```

| HTTP status | Typical `code` | When |
|-------------|----------------|------|
| `400` | `VALIDATION_ERROR` | Invalid input, business rule via `IllegalArgumentException` / `IllegalStateException` |
| `404` | `PRODUCT_NOT_FOUND` | Product not found (`ProductNotFoundException`) |
| `409` | `NOT_ENOUGH_STOCK` | Insufficient stock (`NotEnoughStockException`) |
| `500` | `INTERNAL_ERROR` | Unexpected server error |

---

### Quick reference — all endpoints

| # | Method | Path | Body |
|---|--------|------|------|
| 1 | `POST` | `/products/create` | `CreateProductRequest` |
| 2 | `GET` | `/products` | — |
| 3 | `GET` | `/products/{productId}` | — |
| 4 | `PUT` | `/products/{productId}/price` | `{ "newPrice": 0.01 }` |
| 5 | `PUT` | `/products/{productId}/stock` | `{ "newStock": 0 }` |
| 6 | `PUT` | `/products/{productId}/activate` | — |
| 7 | `PUT` | `/products/{productId}/deactivate` | — |
| 8 | `POST` | `/orders/create` | — |
| 9 | `POST` | `/orders/{orderId}/items` | `{ "productId": 1, "quantity": 1 }` |
| 10 | `POST` | `/orders/{orderId}/start` | — |

---

### Postman collection variables (suggested)

| Variable | Example | Set after |
|----------|---------|-----------|
| `baseUrl` | `http://localhost:8080` | manual |
| `productId` | `1` | step 1 response `id` |
| `orderId` | `1` | step 4 response body |
