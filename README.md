# SystemShell
### Allow Applications to access the system permissions

based on smtshell for samsung devices designed for aosp build intergration.

### Installation
Add the following lines to your local_manifests. {aosp}/.repo/local_manifests/overrides.xml

```xml
<remote name="tharow-services" fetch="https://github.com/Tharow-Services" />
<project name="android_packages_apps_SystemShell" path="packages/apps/SystemShell" remote="tharow-services" />
```
Then add SystemShell to your devices PRODUCT_PACKAGES list. After that it should show up in the next build cycle
