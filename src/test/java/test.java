import java.sql.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class test {
    private static final Logger logger = LoggerFactory.getLogger(test.class);
    private static final int MAX_RETRY_COUNT = 3;

    @Test
    public void testJDBCConnection() {
        Connection con = null;
        int retryCount = 0;

        String url = "jdbc:mysql://localhost:3306/quiz_db"; // 서버 주소
        String userName = "root"; //  접속자 id
        String password = "0000"; // 접속자 pw

        // JDBC 드라이버 로드
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("JDBC 드라이버를 로드하는데에 문제 발생", e);
            return;
        }

        // 접속
        while (retryCount < MAX_RETRY_COUNT) {
            try {
                con = DriverManager.getConnection(url, userName, password);
                logger.info("연결 완료!");
                break;
            } catch (SQLException e) {
                retryCount++;
                logger.error("연결 오류 발생. 재시도 중...", e);
                if (retryCount == MAX_RETRY_COUNT) {
                    logger.error("최대 재시도 횟수 초과. 연결에 실패했습니다.");
                    return;
                }
            }
        }

        // 접속 종료
        try {
            if (con != null) {
                con.close();
                logger.info("연결이 정상적으로 종료되었습니다.");
            }
        } catch (SQLException e) {
            logger.error("연결 종료 중 오류 발생", e);
        }
    }
}