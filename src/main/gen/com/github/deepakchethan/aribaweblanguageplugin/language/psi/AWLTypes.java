// This is a generated file. Not intended for manual editing.
package com.github.deepakchethan.aribaweblanguageplugin.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.deepakchethan.aribaweblanguageplugin.language.impl.*;

public interface AWLTypes {

  IElementType TAG = new AWLElementType("TAG");

  IElementType COMMENT = new AWLTokenType("COMMENT");
  IElementType CRLF = new AWLTokenType("CRLF");
  IElementType WHITE_SPACE = new AWLTokenType("WHITE_SPACE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == TAG) {
        return new AwlTagImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
