# Use this script to add a new project to Agora
# Run this script from inside the /home/Agora/scripts directory

import sys
import subprocess
import os

# We need to make sure that the arguments are valid, otherwise things will break
def getLanguage():
  print "Supported languages are:\nPython\nJava\nC++\nC#"
  while (True):
    lang = raw_input("Enter the language that this project is written in: ")
    if (lang == "Python"):
      print "Is this project written in Python 2 or Python 3?"
      pyVersion = raw_input("Please enter a 2 or 3: ")
      if (pyVersion == "2"):
        return "p2"
      elif (pyVersion == "3"):
        return "p3"
      else:
        print "unrecognized python version " + pyVersion
    elif (lang == "Java"):
      return "java"
    elif (lang == "C++"):
      return "cpp"
    elif (lang == "C#"):
      return "csharp"
    elif (lang == "quit"):
      print "cancelling project addition"
      return false
    else:
      print "\nunrecognized language " + lang + ", please enter a valid language or enter <quit> to cancel\n"

# We need to know the new project's location
def getNewProjectLocation():
  path = raw_input("Enter the absolute path to the new project's base directory: ")
  if (os.path.isDir(path)):
    return path
  else:
    print "\nWe were unable to find a project directory at this location: " + path + "\n"

lang = getLanguage()
if (lang):
  print "We have a valid language: " + lang
  newProjectLocation = getNewProjectLocation()
  if (os.path.isDir(path)):
    name = raw_input("Enter the name to be displayed on the web page: ")
    executable = raw_input("Enter the name of the executable to be run: ")
else:
  print "Nothing should have changed"

# We need to add the entry to the home.html file

# We need to add the entry to the AgoraServlet.java file

# We need to rebuild the project
print "We need to rebuild the Agora project, this should only take a minute or two"
#subprocess.call("cd /home/Agora/guacamole-client/guacamole")
#subprocess.call("./builddeploy.sh")

# We think that is it
