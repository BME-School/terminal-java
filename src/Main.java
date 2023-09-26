import java.util.Scanner;
import java.io.*;
import java.util.LinkedList;



public class Main {
    private static File wd;

    public static void main(String[] args) {
        wd = new File(System.getProperty("user.dir"));
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = scanner.nextLine();
            String[] cmd = line.split(" ");

            if (cmd[0].equals("exit")) {
                System.exit(0);
            } else if(cmd[0].equals("pwd")) {
                pwd(cmd);
            } else if(cmd[0].equals("ls")) {
                ls(cmd);
            } else if(cmd[0].equals("cd")) {
                cd(cmd);
            } else if(cmd[0].equals("rm")) {
                rm(cmd);
            } else if(cmd[0].equals("mkdir")) {
                mkdir(cmd);
            } else if(cmd[0].equals("mv")) {
                mv(cmd);
            } else if(cmd[0].equals("cp")) {
                cp(cmd);
            } else if(cmd[0].equals("cat")) {
                cat(cmd);
            } else if(cmd[0].equals("length")) {
                length(cmd);
            } else if(cmd[0].equals("head")) {
                head(cmd);
            } else if(cmd[0].equals("tail")) {
                tail(cmd);
            } else if(cmd[0].equals("wc")) {
                wc(cmd);
            } else if(cmd[0].equals("grep")) {
                grep(cmd);
            }
        }
    }

    protected static void pwd(String[] cmd) {
        System.out.println(wd);
    }

    protected static void ls(String[] cmd){
        if(cmd.length > 2  || (cmd.length == 2 && !cmd[1].equals("-l"))){
            System.out.println("ls: Helytelen formátum! Help: ls [-l]");
            return;
        }
        boolean showDetails = cmd.length == 2;

        File[] files = wd.listFiles();
        assert files != null;
        for (File file : files) {
            if (showDetails) {
                String type = file.isDirectory() ? "d" : "f";
                System.out.println(type + " " + file.length() + "KB " + file.getName());
            } else {
                System.out.println(file.getName());
            }
        }
    }

    protected static void cd(String[] cmd){
        if(cmd.length != 2){
            System.out.println("cd: Helytelen formátum! Help: cd <dir>");
            return;
        }
        String nextDir = cmd[1];

        if(nextDir.equals("..")){
            File parentFile = wd.getParentFile();
            if (parentFile != null) wd = parentFile;
            else System.out.println("cd: Helytelen formátum vagy nincs ilyen mappa! Help: cd <dir>");
            return;
        }

        File newDir = new File(wd, nextDir);
        if(newDir.isDirectory()) wd = newDir;
        else System.out.println("cd: Helytelen formátum vagy nincs ilyen mappa! Help: cd <dir>");

    }

        protected static void rm(String[] cmd){
            if(cmd.length != 2){
                System.out.println("rm: Helytelen formátum! Help: rm <dir>");
                return;
            }

            File toDelete = new File(wd, cmd[1]);
            if(!toDelete.exists()){
                System.out.println("rm: Nincs ilyen file: " + cmd[1]);
            } else {
                if(!toDelete.delete()) System.out.println("rm: Nem sikerült törölni a filet: " + cmd[1]);
            }
        }

    protected static void mkdir(String[] cmd){
        if(cmd.length != 2){
            System.out.println("mkdir: Helytelen formátum! Help: mkdir <dir>");
            return;
        }

        File newDir = new File(wd, cmd[1]);
        if(!newDir.exists()){
            System.out.println("mkdir: Már létezik ilyen mappa: " + cmd[1]);
        } else {
            if(!newDir.mkdir()) System.out.println("mkdir: Nem sikerült létrehozni a mappát: " + cmd[1]);
        }
    }

    protected static void mv(String[] cmd){
        if(cmd.length != 3){
            System.out.println("mv: Helytelen formátum! Help: mv <file1> <file2>");
            return;
        }
        File old = new File(wd, cmd[1]);
        File dest = new File(wd, cmd[2]);

        if(dest.exists()){
            System.out.println("mv: Már van ilyen file: " + cmd[1]);
            return;
        }


        if(!old.renameTo(dest)) System.out.println("Nem sikerült elmenteni a file-t");
    }

    protected static void cp(String[] cmd){
        if(cmd.length != 3){
            System.out.println("cp: Helytelen formátum! Help: cp <file1> <file2>");
            return;
        }

        File old = new File(wd, cmd[1]);
        File dest = new File(wd, cmd[2]);

        if (!old.exists()) {
            System.out.println("cp: Nincs ilyen file: " + cmd[1]);
            return;
        }

        if (dest.exists()) {
            System.out.println("cp: Már van ilyen file: " + cmd[2]);
            return;
        }

        try (FileInputStream fis = new FileInputStream(old);
             FileOutputStream fos = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            System.out.println("Hiba történt a fájlmásolás során: " + e);
        }

        if(!old.renameTo(dest)) System.out.println("Nem sikerült elmenteni a file-t");
    }
    protected static void cat(String[] cmd) {
        if (cmd.length != 2) {
            System.out.println("cat: Helytelen formátum! Help: cat <file>");
            return;
        }

        File file = new File(wd, cmd[1]);
        if (!file.exists()) {
            System.out.println("rm: Nincs ilyen file: " + cmd[1]);
            return;
        }

        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Hiba történt a fájl olvasása közben: " + e);
        }
    }

    protected static void length(String[] cmd) {
        if (cmd.length != 2) {
            System.out.println("length: Helytelen formátum! Help: length <file>");
            return;
        }
        File file = new File(wd, cmd[1]);
        if(file.exists()) System.out.println("A fájl hossza: " + file.length() + " bájt");
        else System.out.println("length: Nincs ilyen file: " + cmd[1]);

    }

    protected static void head(String[] cmd) {
        int linesToRead = 10;

        if (cmd.length == 4 && cmd[1].equals("-n")) {
            try {
                linesToRead = Integer.parseInt(cmd[2]);
            } catch (NumberFormatException e) {
                System.out.println("head: Érvénytelen érték: " + cmd[2]);
                return;
            }
        } else if (cmd.length != 2) {
            System.out.println("cat: Helytelen formátum! Help: head [-n <n>] <file>");
            return;
        }

        String fileName = cmd[cmd.length - 1];
        File file = new File(wd, fileName);

        if (!file.exists()) {
            System.out.println("head: A fájl nem létezik: " + fileName);
            return;
        }

        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            int linesRead = 0;
            String line;
            while (linesRead < linesToRead && (line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                linesRead++;
            }
        } catch (IOException e) {
            System.out.println("Hiba történt a fájl olvasása közben: " + e);
        }
    }

    protected static void tail(String[] cmd) {
        int linesToRead = 10;

        if (cmd.length == 4 && cmd[1].equals("-n")) {
            try {
                linesToRead = Integer.parseInt(cmd[2]);
            } catch (NumberFormatException e) {
                System.out.println("tail: Érvénytelen érték: " + cmd[2]);
                return;
            }
        } else if (cmd.length != 2) {
            System.out.println("tail: Helytelen formátum! Help: tail [-n <n>] <file>");
            return;
        }

        String fileName = cmd[cmd.length - 1];
        File file = new File(wd, fileName);

        if (!file.exists()) {
            System.out.println("tail: A fájl nem létezik: " + fileName);
            return;
        }


        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            LinkedList<String> list = new LinkedList<String>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
            for(String l: list.subList(Math.max(list.size() - linesToRead, 0), list.size()))
                System.out.println(l);
        } catch (IOException e) {
            System.out.println("Hiba történt a fájl olvasása közben: " + e);
        }
    }

    protected static void wc(String[] cmd){
        if(cmd.length != 2){
            System.out.println("wc: Helytelen formátum! Help: wc <file>");
            return;
        }

        File file = new File(wd, cmd[1]);
        if (!file.exists()) {
            System.out.println("tail: A fájl nem létezik: " + cmd[1]);
            return;
        }

        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            int lineCount = 0;
            int wordCount = 0;
            int charCount = 0;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineCount++;
                String[] words = line.split(" ");
                wordCount += words.length;
                charCount += line.length();
            }
            System.out.println("Sorok száma: " + lineCount);
            System.out.println("Szavak száma: " + wordCount);
            System.out.println("Betűk száma: " + charCount);
        } catch (IOException e) {
            System.out.println("Hiba történt a fájl olvasása közben: " + e);
        }

    }

    protected static void grep(String[] cmd){
        if(cmd.length != 3){
            System.out.println("grep: Helytelen formátum! Help: grep <pattern> <file>");
            return;
        }

        File file = new File(wd, cmd[2]);
        if (!file.exists()) {
            System.out.println("tail: A fájl nem létezik: " + cmd[1]);
            return;
        }

        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if(line.matches("(.*)" + cmd[1] + "(.*)")) System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Hiba történt a fájl olvasása közben: " + e);
        }


    }

}