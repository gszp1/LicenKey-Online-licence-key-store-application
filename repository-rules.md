# REPOSITORY ORGANISATION RULES

## 1. Branches
- **main** - stable version, used for new releases,
- **pre-prod** - testing branch, where all new functionalities that work are added and later tested before being moved to main. Contains all changes that
will be included in next release + fixes for bugs that were caused by new features being merged together,
- **dev** - main development branch, where features land before being added to 
pre-prod. Note that not all changes that are in develop, will end in next release,
- **feature branches** - new features added to the project,
- **refactor branches** - code cleanups, refactors that improve code quality both in terms of code readability and performance,
- **fix branches** - fixes for errors that were introduced during development,
- **hotfix branches** - critical fixes for errors in production code - directly merged into main branch,
## 2. Feature branches naming scheme
### {version}/{project-module}/{type}/feature-name
#### where:
- **version** - version in which developed changes are meant to be included in,
- **project-module** - module which is affected by those changes, mostly backend or frontend as infrastructure is not included in this project,
- **type** - what do these changes introduce - feature, refactor, fix
- **feature-name** - proper name for the feature that is implemented.