# Extensibility

When add a new rule and implement the logic of that new rule, follow these steps:

- Add new class extends abstract class `processor` and override methods (if necessary)
- Update configs in `resources/config.yml` file
- Update static mapping in `ProcessorHelper` class

## Scripts

- Generated in ${project.rootDir}/build/scripts
- Example: ./build/scripts