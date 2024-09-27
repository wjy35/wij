package com.wjy35.wij.run.judge.fetch;

import com.wjy35.wij.run.judge.fetch.exception.FetchFailedException;
import com.wjy35.wij.run.judge.fetch.exception.InternetConnectionLostException;
import com.wjy35.wij.run.judge.fetch.exception.InternetConnectionUnavailableException;
import com.wjy35.wij.util.crawling.BOJCrawler;
import com.wjy35.wij.util.file.IOFileCommand;
import org.jsoup.HttpStatusException;
import java.io.IOException;
import java.net.UnknownHostException;

public class FetcherImpl implements Fetcher {
    private final IOFileCommand ioFileCommand;

    public FetcherImpl(IOFileCommand ioFileCommand) {
        this.ioFileCommand = ioFileCommand;
    }

    @Override
    public void crawlAndFetch(String problemNumber){
        try {
            tryToCrawlAndFetch(problemNumber);
        } catch (HttpStatusException e){
            throw new InternetConnectionLostException();
        } catch (UnknownHostException e){
            throw new InternetConnectionUnavailableException();
        } catch (IOException e){
            throw new FetchFailedException();
        }
    }

    public void tryToCrawlAndFetch(String problemNumber) throws IOException {
        ioFileCommand.deleteAllIOFile();
        ioFileCommand.saveAllBy(BOJCrawler.crawlAllBy(problemNumber));
    }
}
