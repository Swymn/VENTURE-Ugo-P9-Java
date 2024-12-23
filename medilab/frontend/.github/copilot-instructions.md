# Instructions for GitHub Copilot

## Objective
Ensure that all generated code adheres to the principles of clean code, modularity, and clean architecture as outlined by renowned software engineers such as Uncle Bob (Robert C. Martin), Kent Beck, and Martin Fowler. This applies to both Java and Angular projects.

## Guidelines

### General Principles
1. **Readability**: Code should be easy to read and understand.
2. **Simplicity**: Avoid unnecessary complexity.
3. **Consistency**: Follow consistent naming conventions and coding styles.
4. **Modularity**: Break down code into small, reusable, and independent modules.
5. **Single Responsibility Principle**: Each class or module should have one responsibility.
6. **Open/Closed Principle**: Classes should be open for extension but closed for modification.
7. **Liskov Substitution Principle**: Subtypes must be substitutable for their base types.
8. **Interface Segregation Principle**: Many client-specific interfaces are better than one general-purpose interface.
9. **Dependency Inversion Principle**: Depend on abstractions, not on concretions.

### Java Specific Practices
1. **Package Structure**: Organize packages by feature, not by layer.
2. **Class Design**: Use meaningful class names and keep classes focused.
3. **Method Design**: Methods should do one thing and do it well. Keep them short.
4. **Error Handling**: Use exceptions for exceptional conditions. Avoid using exceptions for control flow.
5. **Testing**: Write unit tests for all public methods. Use mocking frameworks where necessary.

### Angular Specific Practices
1. **Component Structure**: Break down the UI into small, reusable components.
2. **Service Design**: Use services for business logic and data management.
3. **State Management**: Use state management libraries like NgRx for complex state.
4. **Module Organization**: Organize modules by feature. Use lazy loading for large modules.
5. **Template Syntax**: Use Angular's template syntax for binding and directives. Avoid using inline styles and scripts.
6. **Dependency Injection**: Use Angular's dependency injection system to manage dependencies.

### Clean Architecture
1. **Separation of Concerns**: Separate the application into layers (e.g., presentation, domain, data).
2. **Dependency Rule**: Inner layers should not depend on outer layers.
3. **Use Cases**: Encapsulate business rules in use case classes.
4. **Entities**: Represent core business objects with entities.
5. **Repositories**: Use repositories to abstract data access.

## Implementation Steps
1. **Plan**: Understand the requirements and design the solution.
2. **Code**: Write the code following the guidelines above.
3. **Review**: Review the code to ensure it adheres to the guidelines.
4. **Test**: Write and run tests to ensure the code works as expected.
5. **Refactor**: Refactor the code to improve its structure and readability.

By following these guidelines, the generated code will be clean, maintainable, and scalable.
