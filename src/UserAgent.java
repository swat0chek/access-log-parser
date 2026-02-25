import java.time.LocalDateTime;

public class UserAgent {
    private final String os;
    private final String browser;

    public UserAgent(String userAgentString) {
        this.os = extractOS(userAgentString);
        this.browser = extractBrowser(userAgentString);
    }

    private String extractOS(String userAgent) {
        if (userAgent.contains("Windows")) {
            return "Windows";
        } else if (userAgent.contains("Macintosh") || userAgent.contains("macOS")) {
            return "macOS";
        } else if (userAgent.contains("Linux")) {
            return "Linux";
        } else {
            return "Другая";
        }
    }

    private String extractBrowser(String userAgent) {
        if (userAgent.contains("Edge")) {
            return "Edge";
        } else if (userAgent.contains("Firefox")) {
            return "Firefox";
        } else if (userAgent.contains("Chrome")) {
            return "Chrome";
        } else if (userAgent.contains("Opera")) {
            return "Opera";
        } else {
            return "Другой";
        }
    }

    public String getOs() {
        return os;
    }

    public String getBrowser() {
        return browser;
    }
}