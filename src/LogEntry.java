import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Locale;

public class LogEntry {

    public enum HttpMethod {
        GET, POST, PUT, DELETE, HEAD, OPTIONS, PATCH, TRACE, CONNECT
    }

    private final String ipAddr;
    private final LocalDateTime time;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final UserAgent userAgent;

    private static final Pattern LOG_PATTERN = Pattern.compile(
            "^(\\S+) \\S+ \\S+ \\[(.+?)\\] \"(\\S+) (\\S+) \\S+\" (\\d+) (\\d+|-) \"([^\"]*)\" \"([^\"]*)\"$");

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z").withLocale(Locale.US);

    public LogEntry(String logLine) {
        Matcher matcher = LOG_PATTERN.matcher(logLine);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Некорректный формат строки лога: " + logLine);
        }

        this.ipAddr = matcher.group(1);
        this.time = LocalDateTime.parse(matcher.group(2), DATE_FORMATTER);
        this.method = HttpMethod.valueOf(matcher.group(3));
        this.path = matcher.group(4);
        this.responseCode = Integer.parseInt(matcher.group(5));

        String sizeStr = matcher.group(6);
        this.responseSize = "-".equals(sizeStr) ? 0 : Integer.parseInt(sizeStr);

        this.referer = matcher.group(7);
        String userAgentStr = matcher.group(8);
        this.userAgent = new UserAgent(userAgentStr);
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }
}