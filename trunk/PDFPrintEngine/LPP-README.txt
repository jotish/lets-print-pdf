Let's Print PDF (LPP) is a companion app for Let's Print Droid.
Let's Print PDF acts as a PDF rendering engine, allowing Let's Print Droid (LPD) to generate PCL and Postscript for PDF files. (Similar to GhostScript.)
To print a PDF file, you must install and run Let's Print Droid. By itself, Let's Print PDF acts only as a PDF viewer.
Let's Print PDF is a minor variation of the MuPDF Android Project.
This app is distributed under the GNU GPL version 3 license.
The source for this app can be found at http://code.google.com/p/lets-print-pdf   

To build the LPP app, first GIT Clone the MuPDF Android Project from :

	http://git.ghostscript.com/?p=mupdf.git;a=tree;f=android;hb=HEAD


LPP version one was built using version 1 build 6:

	git checkout 0cbf73e579846395c7b9f7ebd3f3f229d99104ac

You may wish to build to the latest stable version.

Then read the MuPDF Android README file and perform a full ANT Build as described for MuPDF.

Upon a successful build import the resulting android sub-dir into Eclipse as a new Android project 
setting the "copy source" option.

Overlay the files from http://code.google.com/p/lets-print-pdf onto your new project being
careful not to destroy the newly generated SO's, RES, libs, JNI source, etc.
You may need to hand-merge the manifest files from muPDF and LPP depending on your build level. 

Regards
BlackSpruce
