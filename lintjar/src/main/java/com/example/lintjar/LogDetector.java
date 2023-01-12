package com.example.lintjar;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiMethod;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UCallExpression;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class LogDetector extends Detector implements Detector.UastScanner{

    public static final Issue ISSUE = Issue.create(
            "LogUsage",//id:唯一值，在配置lint屏蔽规则时会使用到
            "Avoid to use android.util.Log",//简短的描述
            "请使用统一的日志工具类!",//对问题的解释，需要注意汉字编码问题可能导致乱码
            Category.CORRECTNESS,//问题类别
            6,//优先级：1-10，数字越大级别越高（越严重）
            Severity.ERROR,//严重程度：Fatal, Error, Warning, Informational, Ignore
            new Implementation(LogDetector.class, Scope.JAVA_FILE_SCOPE)//执行规制：包含探测器和探测范围2个参数
    );

    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("v", "d", "i", "w", "e", "wtf");
    }

    @Override
    public void visitMethodCall(@NotNull JavaContext context, @NotNull UCallExpression node, @NotNull PsiMethod method) {
        if (context.getEvaluator().isMemberInClass(method, "android.util.Log")) {
            context.report(ISSUE, node, context.getLocation(node), "避免调用android.util.Log");
        }
    }
}