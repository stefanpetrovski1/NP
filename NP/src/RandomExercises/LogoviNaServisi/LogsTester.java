package RandomExercises.LogoviNaServisi;

import java.util.*;
import java.util.stream.Collectors;


abstract class Log {
    String service;
    String microservice;
    String message;
    String timestamp;
    String type;

    public Log(String service, String microservice, String message, String timestamp) {
        this.service = service;
        this.microservice = microservice;
        this.message = message;
        this.timestamp = timestamp;
    }

    public static Log createLog(String line) {
        //service_name microservice_name type message timestamp
        String[] parts = line.split("\\s+");
        String serviceName = parts[0];
        String microserviceName = parts[1];
        String type = parts[2];
        String message = Arrays.stream(parts).skip(3).collect(Collectors.joining(" "));
        String timestamp = parts[parts.length - 1];

        if (type.equals("INFO")) {
            return new InfoLog(serviceName, microserviceName, message, timestamp);
        } else if (type.equals("WARN")) {
            return new WarnLog(serviceName, microserviceName, message, timestamp);
        }
        return new ErrorLog(serviceName, microserviceName, message, timestamp);
    }

    public abstract int getSeverity();

    @Override
    public String toString() {
        //service2|microservice2 [ERROR] Log message 97.This error is a fatal error!! 9767 T:9767
        return String.format("%s|%s [%S] %s T:%s",
                service, microservice, type, message, timestamp);

    }
}

class InfoLog extends Log {
    public InfoLog(String service, String microservice, String message, String timestamp) {
        super(service, microservice, message, timestamp);
        this.type = "INFO";
    }

    @Override
    public int getSeverity() {
        return 0;
    }
}

class WarnLog extends Log {
    public WarnLog(String service, String microservice, String message, String timestamp) {
        super(service, microservice, message, timestamp);
        this.type = "WARN";
    }

    private boolean checkForMessage() {
        String sentence = "might cause error";
        return message.contains(sentence);
    }

    @Override
    public int getSeverity() {
        if (checkForMessage())
            return 2;
        return 1;
    }
}


class ErrorLog extends Log {
    public ErrorLog(String service, String microservice, String message, String timestamp) {
        super(service, microservice, message, timestamp);
        this.type = "ERROR";
    }

    @Override
    public int getSeverity() {
        int severity = 2;
        if (message.contains("fatal"))
            severity += 2;
        if (message.contains("exception"))
            severity += 3;

        return severity;
    }
}

class LogCollector {
    Map<String, List<Log>> logsByService;


    public LogCollector() {
        logsByService = new HashMap<>();
    }

    public void addLog(String log) {
        String service = log.split("\\s+")[0];
        Log l = Log.createLog(log);
        logsByService.putIfAbsent(service, new ArrayList<>());
        logsByService.get(service).add(l);
    }

    public void printServicesBySeverity() {
        //Service name: service2 Count of microservices: 3 Total logs in service: 5
        // Average severity for all logs: 3.40
        // Average number of logs per microservice: 1.67

        //TODO: method that takes a service and counts how many logs
        logsByService.entrySet().stream().forEach(entry ->{
            String serviceName= entry.getKey();
            int numberOfMicroservices = entry.getValue().size();
        });

    }

    public void displayLogs(String service, String microservice, String order) {

    }

    public Map<Integer, Integer> getSeverityDistribution(String service, String microservice) {
        return new HashMap<>();
    }
}


public class LogsTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LogCollector collector = new LogCollector();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.startsWith("addLog")) {
                collector.addLog(line.replace("addLog ", ""));
            } else if (line.startsWith("printServicesBySeverity")) {
                collector.printServicesBySeverity();
            } else if (line.startsWith("getSeverityDistribution")) {
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                if (parts.length == 3) {
                    microservice = parts[2];
                }
                collector.getSeverityDistribution(service, microservice).forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
            } else if (line.startsWith("displayLogs")) {
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                String order = null;
                if (parts.length == 4) {
                    microservice = parts[2];
                    order = parts[3];
                } else {
                    order = parts[2];
                }
                collector.displayLogs(service, microservice, order);
            }
        }
    }
}