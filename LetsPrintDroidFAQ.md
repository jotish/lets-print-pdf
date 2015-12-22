# FAQ - Let’s Print Droid (LPD) by BlackSpruce Software #

This is the FAQ file for the Android Printing app "Let's Print Droid" and its companion app Let's Print PDF. Both apps are free and can be found on the Google Play app site.

**How do I add my printer?**

Press the menu button and choose “New Printer”.

**Where is the Menu Button?**

On older devices it was an actual button. On most new devices look along the bottom-right or top-right for a small icon with three dots. On some HTC devices you must long-press the “previous apps” button to see the menu.

**The Network Scan did not find my printer. What gives?**

The network scan uses Multi-cast DNS (mDNS) (a.k.a. Bonjour; a.k.a. ZeroConf) to query your local Wifi network for printers. Not every printer supports this technology. These printers are often labeled as Apple AirPrint compatible. Try downloading a “Bonjour Browser” from Google Play and see if it can find your printer. Not every network router will support mDNS either. Check your router config to see that support for mDNS or UPNP is turned on. Printers can only be found on the same local sub-network as your tablet/phone. (e.g. Both printer and tablet must have IP addresses in range 192.168.2.1 through 192.168.2.254) The mDNS query will not cross sub-nets unless you are a smarty-pants at configuring such things.

There is a second fall-back scan on Line Printer Remote/Line Printer Daemon (LPR/LPD, port 515) that will occur if there are no mDNS responses to the initial scan. If there is a response from your printer on port 515 then an Simple Network Management Protocol (SNMP) query will be sent to gather details on your printer. If there is no suitable SNMP response then it will be flagged as an “Unknown Printer”.


**The scan found my printer but it says it does not have a driver to match.**

Printers are a “Tower of Babel”. Every printer manufacturer creates a different Page Description Language (PDL) to speak to their printer. Most of these PDLs are closed proprietary technologies. Many PDLs are engineered (for cost reasons) to dove-tail with ink-jet and Microsoft Windows GDI print technology. This does not port well to mobile devices. The PDLs tend to vary even among the same brand, as printer technology changes, so you can end up with a dozen “dialects” of the same language. Hewlett-Packard is a good example as they make printers that support the following: PCL3, PCL5e, PCL3GUI, PCL5c, PCLSLEEK, PCL6, PCL-XL, HP-GL/2, PCLm, etc. Let’s Print Droid only contains a limited number of drivers to support the most common openly documented Page Description Languages. The reasons are many; this app is a hobby not a paying job, I possess a limited number of printers to test on, the desired PDL is closed/proprietary, etc. PDLs currently supported in release 1.36 are: image/jpeg, HP-PCL5c, PostScript, PDF, PCL-XL, URF (Apple Universal Raster). You express-post me your printer and I’ll consider writing a driver. ;-)

Well wouldn’t it be great if some industry heavy-weight created a unified standard for everyone to follow? Yes, this is such a great idea that I can think of no less than 5 such groups off the top of my head and they all, of course, promote different technologies. In the mean time how do you print? Look for an app on the Play Market supplied by the manufacturer of your printer, but don’t be surprised when it doesn’t work. In the future when purchasing a printer ask the question: Which PDL’s does it support?

**How do I print a file?**

You can select your file from the built-in file browser or you can “Share” the file from another file browsing app like Astro File Browser. For more ways to initiate a print, press Menu->Browser Mode.

**It’s asking for a QR code. Where is the QR code?**

The QR code it is asking for appears when you do a Test Print. This is just a short-cut mechanism for other users of the same printer; to save them the technical hassles of IP addresses and protocols. If you are just setting the app up for the first time then you have no QR code until you do a test print successfully. If other users will be using the printer, then be nice and tape the test sheet near the printer.

**How do I print a web page?**

Pre-KitKat (OS 4.3 and earlier): From your device’s browser choose the Share menu option and share the link with Let’s Print Droid.

Kit-Kat (OS 4.4 and later) : Download the Let’s Print Framework plugin app and activate it. Then you can use the Print menu choice from the Chrome browser.

**It only printed the Logon Screen.**

Yes this is a problem for pre-KitKat. In order to properly render the web page to PDL, I need to actually load the shared web page inside the app and I don’t know your Face Book/Web Mail/Banking userid and password do I? There are two roads around this; 1) Do a screen print. 2) Use a Browser Add-on to create a PDF of the web page. Most Android browsers have internal add-on features for turning a web page into a PDF and then sharing the PDF with another app like Let’s Print Droid. Android security prevents apps from doing screen captures which may capture private information from another app. Otherwise, I would already know your Banking userid and password and it wouldn’t be an issue.

**How do I do a Screen Print?**

Starting in Android version 4 (Ice Cream Sandwich) and up you can trigger a screen capture by simultaneously holding the Volume-Down and Power buttons for 2 seconds. Then you must open the Android Notify Area (drag down from the upper left) and share the image file with Let’s Print Droid. On older devices you may have to “root” your device to do a screen print. I do not recommend rooting as it can “Brick” your device.

**Where are the files processed? Is this a Cloud Service?**

The files are processed on your device or your print server. The print file and the PDL stay on your local network unless you choose the Google Cloud Print option as the protocol. I do collect anonymous analytics data such as what PDL, protocol, model of printer and Crash information. I collect this information so that I can offer to sell to you “Male Enhancement” products. Either that or I use it code a better app.

**I am trying to print a PDF file with your app and it’s telling me to download another app called Let’s Print PDF. What’s up with that?**

Decoding PDF files into bitmaps for printing is no small task. I have leveraged an open source solution to do this: MuPDF. The best way to package the functionality (since not every needs it) was to farm it out to a second app. The PDF app can act as a PDF viewer as well. It is a damn fine viewer; very fast but it only works on ARM and x86 CPU devices. You can initiate printing of the PDF from either app. The bitmaps are created by Let’s Print PDF, the PDL is generated by Let’s Print Droid and then forwarded to your printer.

**How do I print just one page or a page range from a PDF?**

Choose Menu->Basic Settings. Scroll down to Printing Defaults and un-check the box “Always use these defaults”. You will be prompted at each print for a page range.

**Let’s Print PDF crashes on my device.**

Let’s Print PDF is natively compiled for ARM and x86 CPUs only. This is >98% of Android devices. If you side-load it from outside the PLAY store to a different CPU device; it goes boom.

**Let’s Print Droid/PDF runs out of memory printing my PDF file.**

The rendering of PDF pages into 300 dots-per-inch print files consumes a lot of memory. This is one reason why Google never gave Android built-in print functionality. You could try freeing up some memory by killing some running apps. This was tested successfully on a Samsung Galaxy S Vibrant running Gingerbread with 512Mb, but there were no Services running. If this is continuously an issue you may want do off-device rendering of PDFs. Read the help files in detail for several options. Also if your printer understands PDF natively, you could switch to RAW (no PDL ) for such a printer.

**I am trying to print an MS Office file (DOC,DOCX,PPT,PPTX,XLS,XLSX) and I get File Type not Supported.**

Currently I have no capability to render Office Documents into bitmaps. I am looking to include this in future but it is a large task. There are a couple of ways around this:

> Use an App to convert MS Office format to PDF. There are several of these; some are free. Then share your PDF with my App. Keep in mind that these apps are almost always a Cloud Service, so your DOC gets sent to some server, in the back of a Korean Noodle shop or at the NSA, and then back to your device.

> Use a Linux box with the CUPS Filter technique described in the Linux Help Section.

> For Windows there is a more complex solution described in the Ink Jet Help section.

**I am printing a photo and would like to scale it to full page or size X by Y.**

Choose menu->Basic Settings and check the box labeled “Scale Image to fill page”. There is currently no other scaling option. Also with a small memory device (heap size of 64 Mbytes or less) this will be ignored. Scaling large bitmaps to random sizes is memory thirsty work and I am trying to keep the app running on 512Mb devices. This is another reason why Google did not include print natively on Android.

**Your app says it printed OK but my printer did not print anything.**
-OR-

**Your app says it printed OK but the printer churned out pages of garbage.**

Your printer does not understand the Page Description Language or PDL that is configured for it. First check your printer’s manual to see if you can find what PDL the printer does understand and then choose Menu->Manage Printers to alter your printer’s PDL to one that may work. If this fails read the “Ink Jet Help” section of the help files.

**An error message showed up on the screen but I missed it.**

From the main File Browser press Menu->Help and press Menu->Recent Error Messages.

**I have a USB cabled printer; can I connect it to my Android tablet?**

In most cases: No. Certainly not with Let’s Print Droid. Most USB interfaces require a strong power supply; your tablet’s battery is not enough. Some “USB On-The-Go” interfaces have dropped or lowered the power requirements but I am still not aware of it being used to support a printer (not on a consumer mainstream device anyways). The Android/Linux USB drivers would be a stumbling point as you could not install them without root access. Most devices do not allow root access. If you want to print to a USB printer you will need to connect it a PC and then have the app print via the PC. There are several ways to do that and they are described in the other help sections. See the Microsoft, Linux, or Google Cloud Print help files.

**Does your app print to Bluetooth printers?**

No. There is a special printing protocol available in Bluetooth but I have no printer to test it on.

**I have a new feature suggestion.**

-OR-

**I found a bug in your app.**

Please email the details to me at BlackSpruce.Software@gmail.com

**I love your app. Can I donate?**

Thanks. You can donate Bitcoin from the menu->Donate option or by scanning the QR code on the test print sheet. Also please rate it highly on the Play Market. I have a big ego but there is always room to grow. I have no other donate option at this time.

**Your app is great; do you have an iPad/iPhone version?**

No. I don’t own any Apple products. Too expensive. I am also allergic to designer clothing. If you buy me a Mac computer, an iPhone and pay for the developer software then I will write the app.

**My Samsung phone has a Print option on the menu but it does not work (with your app).**

This is a proprietary Samsung function. It only works with Samsung printers. Talk to Samsung.

**I am trying to print an email from K9 and it says it can’t.**

I tried to decipher what the K9 app Shares in a “Send Intent” and I could not. It certainly was not the email contents or a valid URI. Let me know if you sort it out.

**I am trying to print to a Printer/Server/Linux/CUPS server and I am getting “Timed Out - SocketTimeout”.**

This usually means it cannot reach the IP address you provided for your printer. Is the address correct? Use the printer console buttons to print a status or network report. The correct IP will be listed. Also is your Android device on the Data network when it should be on Wifi? Is there a firewall between the device and the printer?

**I am trying to print to a Printer/Server/Linux/CUPS server and I am getting “Connection Refused”.**

Connection refused most often means that you have the correct IP but the wrong port number. The port numbers are tied to the protocol. So it is likely that the remote printer/server does have any process listening on that port and so does not understand the protocol. Go into Menu->Manage Printers and select the printer and Change the protocol to something else. If you are really convinced that your printer/server is listening on that port then try this debug technique: Download a Telnet client on the play market and connect using the IP, but instead of the standard Telnet port 23 use the port number from protocol (e.g. 631, 515, 9100, etc). Then attempt to Telnet to your printer from your android device. Is the Connection Refused?

For Printer Servers: (windows , Linux, CUPS ) - Sometimes Connection Refused can be a security problem such as a Fire Wall issue or the Printers are not shared correctly. Check your fire wall and see that the port allows incoming connections from the local network. Check your Linux Print server to see that the printers are shared and accessible from the internet (See Linux help section.)

**I am printing to a public printer on campus and need to specify a username like: lpd:://username@servername.edu/FancyLaserPrinter**

Go to Menu->Basic Settings scroll to the “Default Username” and change from LetsPrintDroid to whatever you like.

**I am writing and App and would like to use your app for my printing needs.**

Pre-KitKat devices: See the Menu->Help section “Programming: Integrating your App with LPD”. If you have detailed questions send me an email. Keep in mind that my app won’t work for a slew of consumer ink jet printers. See answer to “The scan found my printer…” above.

Post-KitKat: Install the plugin app “Let’s Print Framework” and read the Android Developer section on printing.

**When I print, I get an extra single sheet with “@PJL SET DUPLEX=ON” printed on it.**

Your printer does not print duplex, go to basic settings and turn it off. It is also possible your printer does not understand PJL (HP Print Job Language). Most printers that understand HP PCL also understand HP PJL and it is a handy thing for tasks like duplexing and Number of Copies, etc. In future I will likely put an option to disable it.

**I am printing to my CUPS server and I get “HTTP response code 426 :: 426 - Upgrade Required".**

CUPS is trying to tell you to use a secure SSL connection. (IPPS instead of IPP). Probably you haven’t properly shared the printer in CUPS so that anonymous users can print to it. Also it maybe that CUPS SSL is configured incorrectly. See http://ariejan.net/2006/11/13/cups-426-upgrade-required/