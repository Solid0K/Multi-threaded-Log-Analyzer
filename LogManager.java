import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogManager {
    public logresult loadLogsData(Path path) throws IOException{
        try(Stream<String> lines=Files.lines(path)){
            Map<String,Long> errors=lines.filter(line->line.contains("Error")).collect(Collectors.groupingBy(
                line->"Error",
                Collectors.counting()
            ));
            Stream<String> lines1=Files.lines(path);
            Map<String,Long> UserActivitys=lines1.map(line->line.split(" ")).filter(part->part.length>=3).collect(Collectors.groupingBy(
                part->part[2],
                Collectors.counting()
            ));
            lines1.close();
            return new logresult(errors,UserActivitys);
        }
    }
}
