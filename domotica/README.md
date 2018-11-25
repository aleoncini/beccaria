# beccaria - All in One Application
## All API modules in a single Wildfly Swarm executable
This module contains all logical components assembled together in one single application.
The modules of the application are:
- core
- hub
- telegram
- rules
- timers
- auditing

Within the application modules are organized in packages, in the cloud section the modules will be different authonomous micro services.
Only the **core** module, which contains the data model and other common utils, is referenced by the other modules.
