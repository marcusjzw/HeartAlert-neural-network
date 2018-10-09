# README: nRF52 Embedded

## How to Run

To spare dealing with GCC and Yotta, the easiest way to run the code is to use the online mbed OS compiler (this was used for development). The two files really of use are located in the `source/` folder, called HeartRateService.h and main.cpp. These files are self-written (with help from example code), the rest is boilerplate.

1. Go to https://os.mbed.com/compiler/. Make an account if needed.
2. Connect the nRF52-DK via Micro-USB to the PC. Ensure it is detected by the PC, it should come up under Removable Storage as 'DAPLINK'.
3. Navigate here: https://os.mbed.com/users/marcusjzw/code/HRS-Security/. It is the public repository link for the code as shown above. Click 'Import into Compiler'.
4. Once imported, simply hit 'Compile'. After a short while, a prompt will ask to download a .bin file.
5. Download  the .bin file onto the nRF52-DK by directing the .bin to the root folder of the removable storage device (DAPLINK). Lights should flash that indicate a programming occurring. 
6. Done!


