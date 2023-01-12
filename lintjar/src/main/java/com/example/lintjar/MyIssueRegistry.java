package com.example.lintjar;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.ApiKt;
import com.android.tools.lint.detector.api.Issue;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class MyIssueRegistry extends IssueRegistry {

    @Override
    @NotNull
    public List<Issue> getIssues() {
        return Arrays.asList(LogDetector.ISSUE);
    }

    @Override
    public int getApi() {
        return ApiKt.CURRENT_API;
    }
}