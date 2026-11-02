# Week 7 - Answers and Notes

Practical Session 7: Inheritance and Method Overriding

## Task 2, Step 2.4 - Overriding Analysis

### 1. What does the `@Override` annotation do?

It tells the compiler that the method below is *intended* to override a method
declared in a superclass. The compiler then checks that a method with the same
name and parameter list really exists somewhere up the hierarchy. If it does not
(for example, because of a typo like `calculateSalery()`), compilation fails
instead of silently creating a new, unrelated method. It also documents the
intent to anyone reading the code. The annotation is optional, but it catches
exactly the kind of mistake that is otherwise very hard to see.

### 2. Why is `super.calculateSalary()` used inside the overridden method?

Because a `Manager`'s pay is the normal employee calculation *plus* a bonus. The
base calculation already lives in `Employee`, so calling
`super.calculateSalary()` reuses it instead of copying `return baseSalary;` into
`Manager`. If the parent's rule later changes (for example, adding an allowance),
`Manager` automatically picks up the new rule. It also respects encapsulation:
`Manager` does not need to know how the base salary is computed.

### 3. What would happen if we removed `super.displayInfo()` from `Manager`?

`Manager.displayInfo()` would completely replace the parent version, so the
output would show only the bonus and the total salary. The employee's name and
base salary would disappear from the report even though those fields still
exist. Overriding replaces the whole method, not just part of it; `super` is how
you keep the inherited part and extend it.

## Task 3 - `private`, `protected`, and `public`

- **`private`** - accessible only inside the declaring class. If `name` and
  `baseSalary` were private, `Manager` could not use them directly, and
  `printNameDirectly()` would not compile. It would have to go through a getter
  or a protected method supplied by `Employee`.
- **`public`** - accessible from everywhere. Any class could read *and write*
  `name` and `baseSalary`, so a caller could set a negative salary or rename an
  employee without validation. That breaks encapsulation.
- **`protected`** - accessible inside the same package and inside any subclass
  (even a subclass in another package). It is the middle ground: subclasses can
  extend the parent's state without exposing it to the whole world. In this
  exercise `printNameDirectly()` works precisely because `name` is `protected`.

`protected` is still weaker encapsulation than `private`, so it should be used
only for fields that subclasses genuinely need. A common alternative is to keep
fields `private` and provide `protected` getters/setters.

## Task 4, Step 4.4 - Inheritance vs. Composition

### 1. Why is composition better for `Car` and `Engine`?

Because the relationship is **has-a**, not **is-a**. A car is not a kind of
engine; it *contains* one and delegates work to it. With composition:

- The engine can be replaced or upgraded (petrol, electric) without changing
  what a `Car` is.
- `Engine` can be reused by other classes (a boat, a generator) on its own.
- `Car` does not inherit `Engine`'s entire interface. With `Car extends Engine`,
  a car would literally be an engine and would inherit every engine behaviour,
  including ones that make no sense for a car.
- The two classes stay loosely coupled: changes inside `Engine` do not force
  changes in `Car` as long as the `start()` contract stays the same.

`Car extends Engine` would also make the hierarchy fragile: a `Car` cannot have
zero engines, two engines, or a different kind of engine, and any future
`Engine` subclass would have to be a `Car` as well.

### 2. When would you use inheritance instead?

When the relationship is a true **is-a** and the subclass can safely stand in for
the superclass. For example, `SportsCar extends Car` and `Truck extends Vehicle`:
a sports car *is a* car, a truck *is a* vehicle. They share the parent's state
and behaviour (registration, speed, braking) and add or refine behaviour of their
own (turbo boost, load capacity). Inheritance is appropriate when the subclass
specialises the parent rather than merely using it.

## Reflection Questions

### 1. Why is inheritance referred to as an "is-a" relationship?

Because a subclass object *is a* specialised version of the superclass. A `Dog`
is an `Animal`, a `Circle` is a `Shape`, a `Manager` is an `Employee`. This is
not just wording: a subclass object can be used anywhere the superclass type is
expected (for example, an `Employee` variable can hold a `Manager`). It inherits
the parent's fields and methods and adds or overrides behaviour.

### 2. How does overriding differ from overloading?

**Overriding** happens between a superclass and a subclass. The method has the
same name, the same parameter list, and a compatible return type; the subclass
provides a new implementation that replaces the inherited one. It is resolved at
**runtime**, based on the object's actual type (dynamic dispatch), and is marked
with `@Override`.

**Overloading** happens when several methods in the same class share a name but
have **different parameter lists** (different number, types, or order). The
methods are independent, they can have different return types if the parameters
differ, and the compiler chooses which one to call at **compile time** based on
the arguments.

| | Overriding | Overloading |
|---|---|---|
| Where | Superclass / subclass | Same class (or inherited) |
| Signature | Same name and parameters | Same name, different parameters |
| Chosen | At runtime (actual object type) | At compile time (argument types) |
| Purpose | Replace inherited behaviour | Offer variants of an operation |

### 3. If a superclass has `void doSomething()` and a subclass overrides it, which version runs for a subclass object?

The **subclass** version runs. Java uses dynamic dispatch: the method is chosen
from the object's real runtime type, not from the type of the variable holding
it. So both `Sub s = new Sub(); s.doSomething();` and
`Super s = new Sub(); s.doSomething();` execute `Sub.doSomething()`. The
exception is `static`, `private`, and `final` methods, which are not overridden
and are chosen from the variable's declared type.

### 4. In your own words, why is composition sometimes preferred over inheritance?

Inheritance couples a subclass very tightly to its parent: it inherits the
parent's entire public interface and depends on how the parent implements its
methods (the "fragile base class" problem). Composition only depends on the
small interface of the part being used, so classes stay independent and parts
can be swapped at runtime. It is the right choice when the relationship is
"has-a", when the part is replaceable, or when you only want to reuse behaviour
rather than claim a type. Inheritance is reserved for genuine "is-a"
relationships where substitutability makes sense.
