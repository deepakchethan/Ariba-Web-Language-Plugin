// This is a generated file. Not intended for manual editing.
package com.github.deepakchethan.aribaweblanguageplugin.language;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.deepakchethan.aribaweblanguageplugin.language.psi.AWLElementType;
import com.github.deepakchethan.aribaweblanguageplugin.language.psi.AWLTokenType;
import com.github.deepakchethan.aribaweblanguageplugin.language.psi.impl.*;

public interface AWLTypes {

  IElementType PROPERTY = new AWLElementType("PROPERTY");

  IElementType COMMENT = new AWLTokenType("COMMENT");
  IElementType CRLF = new AWLTokenType("CRLF");
  IElementType KEY = new AWLTokenType("KEY");
  IElementType SEPARATOR = new AWLTokenType("SEPARATOR");
  IElementType VALUE = new AWLTokenType("VALUE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == PROPERTY) {
        return new AWLPropertyImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
