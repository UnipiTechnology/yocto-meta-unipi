# Licensing Information

This document provides licensing information for the Unipi Edge Yocto BSP.

---

## Overview

This BSP is composed of multiple open-source components originating from the **Yocto Project** ecosystem and third-party upstream projects.

Each component is licensed under its respective open-source license.

---

## License Compliance

The Yocto Project build system provides mechanisms to track and collect license information automatically.

Integrators are responsible for:

- Reviewing all licenses applicable to the selected image and packages
- Ensuring compliance with license terms and obligations
- Providing required license texts and notices with the final product

---

## Obtaining License Information

Detailed license data can be generated using standard Yocto tooling:

```bash
bitbake <image> -c populate_lic
```

This command generates:

- A complete list of licenses
- License text files
- Package-to-license mappings

The output is located in the Yocto build directory and should be reviewed as part of the release process.

---

## Disclaimer

This document does not constitute legal advice.  
License compliance remains the responsibility of the product owner or integrator.
