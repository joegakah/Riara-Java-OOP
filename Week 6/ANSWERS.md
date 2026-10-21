# Week 6 - Answers and Notes

Practical Session 6: Deep Dive into Classes and Objects

## Task 1, Step 1.5 - Encapsulation Analysis

### 1. Why is `BadStudent` considered a bad design?

All of its fields (`name`, `age`, `gpa`) are `public`. Any class that holds a
`BadStudent` reference can read or write those fields directly, with no checks
at all. That allowed the test program to create a student aged `-100` with a GPA
of `15.5` - states that are meaningless in the real world. The class cannot
defend its own data, so every piece of code that touches a `BadStudent` has to
duplicate the same validation logic, and one forgetful piece of code corrupts the
object for everyone else. The internal representation is also exposed, so the
class can never change how it stores the data without breaking all of its
callers.

### 2. How does `GoodStudent` prevent invalid data?

It hides the fields behind `private` and forces every write through a setter
that validates first:

- `setAge()` only accepts `0 <= age <= 150`.
- `setGpa()` only accepts `0.0 <= gpa <= 4.0`.

The constructor also calls those same setters (instead of assigning directly),
so an object cannot even be born invalid. When an invalid value arrives, the
setter prints an error and leaves the old value untouched - after
`good.setAge(-100)` the age is still `20`.

### 3. What is the purpose of `private`?

`private` makes a member accessible only inside its own class. It is the
mechanism that creates the "capsule": the object decides which values are legal
and no outside code can bypass those decisions. It also hides implementation
details (a class could later store `age` as a `LocalDate` birth date without
breaking callers) and keeps the public surface of the class small and
intentional.

### 4. What is the purpose of getters and setters?

They are the controlled gateway to private fields:

- **Getters** expose a value for reading without handing out write access.
- **Setters** allow modification, but only after validation and any related
  side effects.

They also let a class be selective: `BankAccount` has `getBalance()` but
deliberately no `setBalance()`, and `Book` exposes `isAvailable()` but lets
availability change only through `borrowBook()` / `returnBook()`. Note that a
getter/setter pair that does no validation and no conversion is often no better
than a public field - the value comes from the rules they enforce, not from the
methods themselves.

## Reflection Questions

### 1. Why do we make fields private instead of public?

So the class, not its callers, controls its own state. Private fields guarantee
that every read/write goes through code the class author wrote, which is where
validation, business rules, and side effects live. Public fields make every
caller a co-author of the object's state, and any bug in any caller becomes a
corrupted object everywhere.

### 2. What is the main purpose of a constructor?

To put a newly created object into a valid, usable state. It runs exactly once
per object, receives the values the object needs to exist (an account number, a
book title, ...), and should leave no field unset or inconsistent. Validation
belongs here too, so an invalid object can never be created.

### 3. How does the `this` keyword help avoid confusion?

`this` is a reference to the current object. It disambiguates a field from a
parameter with the same name (`this.name = name;` assigns the parameter to the
field, while `name = name;` assigns the parameter to itself). It also lets a
constructor call another constructor of the same class (`this(id, name, 25000.0)`),
and it is used to return or pass the current object.

### 4. In the `BankAccount` class, why didn't we create a `setBalance()` method?

Because the balance is not an independent value - it is the *result* of
transactions. A `setBalance()` would let any caller overwrite the balance with
any number, including a negative one, bypassing deposits, withdrawals, and the
insufficient-funds rule, and it would destroy the audit trail of how the money
moved. Instead, `balance` is changed only by `deposit()` and `withdraw()`, each
of which enforces the banking rules (`amount > 0`, sufficient funds). The getter
is enough for reading. This is the difference between exposing data and exposing
behaviour.
