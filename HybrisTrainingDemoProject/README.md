#Introduction
This is sample project designed in an internal training. This is aimed to observe the patterns in SAP Hybris.

#Requirements
You need Hybris version 5.4 for this project to be fully working with it.

#How to setup
* Clone the project in your in your hybris root folder,
* Run a command line/terminal,
* Issue setantenv.bat/setantenv.sh,
* Issue ant clean all command to install the project,
* Fire up your hybris server,
* Navigate to the admin console and initialize your system,
* After a successfully initialization check it in HMC if it is listed there.

#Notes
* This project will override your localextensions.xml file make sure that you've already backed up your existing file to ward off any issues!
* The project has additional test data in the impex file for the project which is very small only for testing purposes.