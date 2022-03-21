// This is a generated file. Not intended for manual editing.
package com.github.deepakchethan.aribaweblanguageplugin.language.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.deepakchethan.aribaweblanguageplugin.language.psi.AWLTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.deepakchethan.aribaweblanguageplugin.language.psi.*;

public class AwlTagImpl extends ASTWrapperPsiElement implements AwlTag {

  public AwlTagImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull AwlVisitor visitor) {
    visitor.visitTag(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof AwlVisitor) accept((AwlVisitor)visitor);
    else super.accept(visitor);
  }

}
