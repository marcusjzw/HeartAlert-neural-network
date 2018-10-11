# README: nRF52 Embedded

## How to Run

To spare dealing with GCC and Yotta configuration, the easiest way to run the code is to use the online mbed OS compiler (this was used for development). This is highly recommended to save time.

1. Go to https://os.mbed.com/compiler/. An account has already been made for the purposes of showcasing running.
    Username: marcusjzw2
    Password: uoa12345
    This account should already have the Nordic nRF52-DK compiler added. You can check this by going to the top-right and clicking "My console", and under "My hardware" it should say Nordic nRF52-DK.
2. Connect the nRF52-DK via Micro-USB to the PC. Ensure it is detected by the PC, it should come up under Removable Storage as 'DAPLINK'.
3. Navigate here: https://os.mbed.com/users/marcusjzw/code/HRS-Security/. It is the public repository link for the code as shown above. Click 'Import into Compiler'.
4. Once imported, simply hit 'Compile'. After a short while, a prompt will ask to download a .bin file.
5. Download  the .bin file onto the nRF52-DK by directing the .bin to the root folder of the removable storage device (DAPLINK). Lights should flash that indicate a programming occurring. 
6. To connect the nRF52-DK to the phone, pairing must happen first. To do this, ensure the device is still connected to the PC and open a serial program (Termite has been used and works). Make sure the correct COM port is configured to listen for the nRF52 device.
7. Configure the connection with these settings: The baud rate is 9600, there are 8 data bits, 1 stop bit and no parity bit. 
8. On the Android phone, go to Settings -> Bluetooth -> Tap on the device called "VT_HRM". A prompt on the phone should appear asking for the passkey.
9.  The passkey is a six digit number which should now pop up on Termite. Enter these six digits onto the phone. 
10. The phone is now paired. You can now open Android Studio and load the HeartAlert app (this would also help with checking logging data if you're interested). 
11. Select VT_HRM. **MAKE SURE AFTER THIS, YOU WAIT FOR AT LEAST 30 SECONDS. THIS IS BECAUSE THE SOCKET AUTOMATICALLY TRIES TO DISCOVER A POLAR H7 UUID - WAIT FOR THE TIMEOUT!** To see all the processes going on, make sure Android Studio is open while the HeartAlert app is open and click 'Logcat' on the bottom left to see the log messages. 
12. The log messages should eventually start reporting receipt of RR intervals. These should be logged on the phone. 
13. After 60 intervals, the Neural Network button at the bottom of the app should be unlocked and the received intervals can be classified.
14. Done!

## How to load new VT datasets

- Navigate to `signalprocessing/onset_prediction_vtvf/RRdata1/vt/`
- There is a file called `cplusplus_arraygen.py`. The objective of this Python script is to produce a code snippet which is pasted into the mbed OS nRF52 source code, to swap out the datasets emitted. 
- The classifier present on the smartphone has been trained with 75% of data. The .txt files in this folder from 77_.txt onwards (up until 105_.txt) are all examples of ventricular tachycardia that has not been used as training data. Thus, they can be used as testing data.
- Call the script using `python cplusplus_arraygen.py 77_.txt`. You can replace 77_.txt with any .txt file from 77 to 105. 
- Copy the code generated onto Line 27 of main.cpp in the online mbed OS compiler. It should be obvious which one; it's the long line initiating the rriValues array.
- Click Compile, and follow the steps in `How to Run` to load the new .bin
- Test the connection using the app. You (usually) do not need to bond the device to the belt again, but if the connection does not seem to work then a repairing may be necessary.


