
#MACHINE_EXTRA_RRECOMMENDS:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'unipi-id', 'kernel-module-unipi-id kernel-module-unipi-rtc kernel-module-unipi-rfkill kernel-module-unipi-mfd', '', d)}"
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'unipi-id', 'kernel-module-unipi-id kernel-module-unipi-rtc kernel-module-unipi-rfkill kernel-module-unipi-mfd', '', d)}"
