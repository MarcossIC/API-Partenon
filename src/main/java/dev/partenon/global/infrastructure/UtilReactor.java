package dev.partenon.global.infrastructure;

import dev.partenon.global.domain.abstractcomponents.UtilReactorPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Service;
import reactor.netty.http.client.PrematureCloseException;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@Log4j2
public class UtilReactor implements UtilReactorPort {

    public static final Retry catchingQueryTimeoutException = Retry.fixedDelay(3, Duration.ofSeconds(2))
            .filter(it -> it instanceof PrematureCloseException || it instanceof QueryTimeoutException)
            .doAfterRetry(retrySignal ->
                    log.info("iteration: {}, cause: {} {}",
                            retrySignal.totalRetriesInARow(),
                            retrySignal.failure().getClass().toString(),
                            retrySignal.failure().getMessage())
            ).onRetryExhaustedThrow((retrySpec, retrySignal) -> retrySignal.failure());

}
