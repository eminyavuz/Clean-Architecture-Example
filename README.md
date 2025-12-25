# Clean Architecture Example (Java)

This repository is a **small demo project** created to demonstrate the **core principles of Clean Architecture** using Java.

The main focus is **domain-first design**, where business rules are modeled independently of frameworks, databases, or UI layers.

---

## 🎯 Purpose

- Demonstrate Clean Architecture fundamentals
- Show a **framework-independent domain layer**
- Keep business logic inside entities
- Avoid anemic models and framework-driven design

This project is intentionally **small and simple** to keep the architecture clear.

---

## 🧱 Architecture Overview

- Domain layer is the core
- Dependencies always point inward
- Entities contain business rules and protect invariants
- Frameworks are treated as optional details

---

## 📦 Current Structure

domain
├── entity
│ ├── Order
│ ├── OrderItem
│ └── Product
└── enums
└── Status


---

## 🧠 Domain Design Notes

- `Order` is the aggregate root
- Business rules (status transitions, validations) live inside entities
- `OrderItem` stores a snapshot of product data
- Order total price is **calculated**, not stored

---

## 🚧 Out of Scope (For Now)

- Controllers / REST APIs
- Spring Boot
- JPA / Database integration

These will be added later while preserving Clean Architecture boundaries.

---

## ✅ Key Idea

> Business rules come first.  
> Frameworks come last.

---

## 📄 License

Educational purpose only.  
Feel free to use and adapt.

