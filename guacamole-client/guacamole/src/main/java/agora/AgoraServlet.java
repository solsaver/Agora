package agora;

import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.lang.Thread;

/**
 * Servlet implementation class AgoraServlet
 */

public class AgoraServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public static class Tuple {
    String dir;
    String exec;
    String lang;
    public Tuple(String d, String e, String l) {
      dir = d;
      exec = e;
      lang = l;
    }
  }
    
  public AgoraServlet() {
     
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Need some way to differentiate between program name and program path (e.g. "mario-cart" vs "108/108-final-examples/mario-cart/main.py"), but they could be the same.
    // The above was a comment from Campo-Webster, we hope to solve this with the Tuple class

    List<String> project_list = Files.readAllLines(Paths.get("/home/Agora/resources/project_listing.txt"), StandardCharsets.US_ASCII);
    HashMap<String, Tuple> programs;
    programs = new HashMap<String, Tuple>();
    for (int i = 0; i < project_list.size(); i++) {
      String[] proj = project_list.get(i).split(",");
      programs.put(proj[0], new Tuple(proj[1], proj[2], proj[3]));
    }

    String displayWebName = request.getParameter("program");
    String directory = programs.get(displayWebName).dir;
    String progName = programs.get(displayWebName).exec;
    String langVersion = programs.get(displayWebName).lang;
    System.out.println("Language version is " + langVersion);

    Process P1 = new ProcessBuilder().inheritIO().command("/home/Agora/blah.sh" , directory, progName, langVersion).start();

    // Run start.sh script to start the vnc server with a python program and version
    Process proc = new ProcessBuilder().inheritIO().command("/home/Agora/start.sh" , directory, progName, langVersion).start();

    // Read from the file /home/Agora/pids/recent.txt - which contains the most recently started process.  Use a delay 
    // to make sure the file has already been written to by start.sh when we read it.
    // maybe also remove the delay in the angular reload
    try {
            System.out.println("about to sleep for 0.5 seconds");
            Thread.sleep(500);
    } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
    }
    List<String> lines = Files.readAllLines(Paths.get("/home/Agora/pids/recent.txt"), StandardCharsets.US_ASCII);
    String myPid = lines.get(0);
    System.out.println("the most recent pid is " + myPid);

    Process P2 = new ProcessBuilder().inheritIO().command("/home/Agora/blah.sh" , directory, progName, langVersion).start();

    response.setContentType("text/plain");  
    response.setCharacterEncoding("UTF-8"); 
    response.getWriter().write(myPid); 
  }

  
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  
 }
}
