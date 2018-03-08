/*
 * MIT License
 *
 * Copyright (c) 2016-2018 The FredBoat Org https://github.com/FredBoat/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.fredboat.quarterdeck.backend.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by napster on 05.05.17.
 * <p>
 * Provides access to the values of the property file generated by whatever git info plugin we're using
 * <p>
 * Requires a generated git.properties, which can be achieved with the gradle git plugin
 */
@Component
@SuppressWarnings("WeakerAccess")
public class GitRepoState {

    private static final Logger log = LoggerFactory.getLogger(GitRepoState.class);

    private final String branch;
    private final String commitId;
    private final String commitIdAbbrev;
    private final String commitUserName;
    private final String commitUserEmail;
    private final String commitMessageFull;
    private final String commitMessageShort;
    private final long commitTime;

    @SuppressWarnings("ConstantConditions")
    public GitRepoState() {

        Properties properties = new Properties();
        try {
            properties.load(GitRepoState.class.getClassLoader().getResourceAsStream("git.properties"));
        } catch (NullPointerException e) {
            log.info("Failed to load git repo information. Did you build with the git gradle plugin? Is the git.properties file present?");
        } catch (IOException e) {
            log.info("Failed to load git repo information due to suspicious IOException", e);
        }

        this.branch = String.valueOf(properties.getOrDefault("git.branch", ""));
        this.commitId = String.valueOf(properties.getOrDefault("git.commit.id", ""));
        this.commitIdAbbrev = String.valueOf(properties.getOrDefault("git.commit.id.abbrev", ""));
        this.commitUserName = String.valueOf(properties.getOrDefault("git.commit.user.name", ""));
        this.commitUserEmail = String.valueOf(properties.getOrDefault("git.commit.user.email", ""));
        this.commitMessageFull = String.valueOf(properties.getOrDefault("git.commit.message.full", ""));
        this.commitMessageShort = String.valueOf(properties.getOrDefault("git.commit.message.short", ""));
        this.commitTime = Long.parseLong(String.valueOf(properties.getOrDefault("git.commit.time", "0"))); //epoch seconds
    }

    public String getBranch() {
        return this.branch;
    }

    public String getCommitId() {
        return this.commitId;
    }

    public String getCommitIdAbbrev() {
        return this.commitIdAbbrev;
    }

    public String getCommitUserName() {
        return this.commitUserName;
    }

    public String getCommitUserEmail() {
        return this.commitUserEmail;
    }

    public String getCommitMessageFull() {
        return this.commitMessageFull;
    }

    public String getCommitMessageShort() {
        return this.commitMessageShort;
    }

    /**
     * @return commit time in epoch seconds
     */
    public long getCommitTime() {
        return this.commitTime;
    }
}
