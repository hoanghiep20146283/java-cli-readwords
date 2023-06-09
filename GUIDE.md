# Extensibility

When add a new rule and implement the logic of that new rule, follow these steps:

- Add new class extends abstract class `processor` and override methods (if necessary)
- Update configs in `resources/config.yml` file
- Update static mapping in `ProcessorHelper` class
- Design patterns:
  - Strategy pattern: [ProcessorHelper](src/main/java/com/lumera/wordsearch/service/ProcessorHelper.java)
  - Bridge pattern: [SearchCommand](src/main/java/com/lumera/wordsearch/command/SearchCommand.java)

## Scripts

- Generated in ${project.rootDir}/build/scripts
- Example command:  .\build\scripts\WordSearch class="{isogram""|"palindrome"}""" maxlength=100


## Logging 

- Generated log location: ${project.rootDir}/logs
- Delete log files after `gradlew clean` task
 
# Test & test coverage report

- Generate by `gradlew test` or `gradlew jacocoTestReport` task
- Test report location: [index.html](build/reports/tests/test/index.html)
- Test coverage report location: [index.html](build/jacocoHtml/index.html)
