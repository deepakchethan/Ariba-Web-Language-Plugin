// This is a generated file. Not intended for manual editing.
package com.github.deepakchethan.aribaweblanguageplugin.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.deepakchethan.aribaweblanguageplugin.language.AWLTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.deepakchethan.aribaweblanguageplugin.language.psi.*;

public class AWLPropertyImpl extends ASTWrapperPsiElement implements AWLProperty {

  public AWLPropertyImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull AWLVisitor visitor) {
    visitor.visitProperty(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof AWLVisitor) accept((AWLVisitor)visitor);
    else super.accept(visitor);
  }

}
