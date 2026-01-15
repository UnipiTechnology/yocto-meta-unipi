# Yocto BSP for Unipi Edge PLCs

This repository provides the Board Support Package (BSP) for [**Unipi*](https://www.unipi.technology/) IoT gateways and industrial PLCs, based on the [**Yocto Project**](https://www.yoctoproject.org/).

The BSP is designed for reproducible, maintainable builds of embedded Linux images used for Unipi devices.  
The build environment is orchestrated using [**KAS**](https://github.com/siemens/kas), which provides a declarative and reproducible way to manage Yocto layers, configurations, and dependencies across different host systems and CI environments.

---

## Supported Hardware

This BSP is **customer-facing** and currently supports **Unipi Edge** devices only.

The following Unipi Edge models are supported or under active development:

- **Unipi Edge E410**
- **Unipi Edge E411**
- **Unipi Edge E412**
- **Unipi Edge E413**
- **Unipi Edge E414** *(work in progress)*

> ⚠️ Other Unipi product families are **not supported** by this BSP at this time and require separate BSPs or platform-specific integrations.

---

## Getting Started

### 1. Host System Requirements

Ensure the following tools are installed on your build host:

- [`git`](https://git-scm.com/install/) – source code management
- [`kas`](https://kas.readthedocs.io/en/latest/userguide/getting-started.html) – Yocto build orchestration tool
- [`rpiboot`](https://github.com/raspberrypi/usbboot) – utility for flashing images to Unipi Edge devices

A Linux host system is required. Using a recent LTS distribution is recommended.

---

### 2. Clone the Repository

```bash
git clone https://git.unipi.technology/yocto/meta-unipi.git
```

---

### 3. Image Creation (Build)

Start the image build using **KAS**. If no image name is specified, `base-os` is used by default:

```bash
kas build kas-edge.yml <image>
```

Example:

```bash
kas build kas-edge.yml base-os
```

The build output will be generated in the `build/tpm/deploy/` directory.

---

### 4. Image Deployment (Flashing)

Image deployment procedures are **currently supported for Unipi Edge devices only**.

After a successful build, locate the generated `.wic` image:

```text
build/tmp/deploy/images/<machine>/*.wic
```

#### Unipi Edge Flashing

Flashing Unipi Edge devices is performed using `rpiboot` and the initialization mode. The below example expects Linux host system.

```bash
cd <directory_where_rpiboot_is>/mass-storage-gadget64
sudo rpiboot -d .
```
Connect the USB-C cable to the INIT port on the Unipi Edge and to your host system, press and hold the SERVICE (SVC) button and then connect 24 V DC power supply. Wait until rpiboot uploads
firmware and on your host systems appears usb storage. If some filesystems from 
usb storage were automounted, unmount them. Upload Wic image to MMC
```bash
sudo dd if=build/tmp/deploy/<machine>/<image>.wic of=</dev/sdX> bs=1M
```

> 📘 **Further informations are available in the Unipi Knowledge Base:**  
> https://kb.unipi.technology/en:hw:004-edge:04-image

---

## Branching and Versioning Strategy

The branching model of this repository follows [**Yocto Project release naming**](https://www.yoctoproject.org/development/releases/).  
Each branch corresponds to a specific Yocto release and contains the BSP state validated for that release.

---

## Supported Yocto Releases

| Yocto Release | Branch Name | Product Family | Support Status | Notes |
|---------------|------------|----------------|----------------|-------|
| Scarthgap     | `scarthgap`| Edge           | Supported      |  |

Future Yocto releases will be supported according to Unipi product lifecycle and validation policies.
