# BSP for Unipi PLC

**Supported machines**

- Unipi Edge E410
- Unipi Edge E411
- Unipi Edge E412
- Unipi Edge E413
- Unipi Edge E414 - not finished
- Unipi Zulu based PLC (Patron) - not finished

## Getting started

To have something ready to install on one of the supported boards, you need to

**(1)** Install the minimal host system requirements.

- `git`
- `kas`
- `rpiboot`


**(2)** Pull the repository

```bash
git clone https://git.unipi.technology/UniPi/yocto/meta-unipi.git
```

**(3)** Start the build by running

```bash
kas build kas-edge.yml <image [default: base-os]>
```
or

**(4)** Locate the `*.wic` image file under `build/tmp/deploy/<machine>/` and flash it
to MMC using tool `rpiboot`.
```bash
cd <directory_where_rpiboot_is>/mass-storage-gadget64
sudo rpiboot -d .
```
Press service button on Edge and power-on the device. Wait until rpiboot uploads
firmware and on your host systems appears usb storage. If some filesystems from 
usb storage were automounted, unmount them. Upload Wic image to MMC
```bash
sudo dd if=build/tmp/deploy/<machine>/>image>.wic of=</dev/sdX> bs=1M
```


## Branching and version control strategy

The branches in this repo are aligned with the Yocto project releases.

Currently supported releases:
- _scarthgap_

