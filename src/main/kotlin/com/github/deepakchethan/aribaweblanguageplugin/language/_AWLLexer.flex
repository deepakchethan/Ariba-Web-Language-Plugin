package com.github.deepakchethan.aribaweblanguageplugin.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.github.deepakchethan.aribaweblanguageplugin.language.psi.AWLTypes.*;

%%

%{
  public _AWLLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _AWLLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+


%%
<YYINITIAL> {
  {WHITE_SPACE}      { return WHITE_SPACE; }

  "COMMENT"          { return COMMENT; }
  "CRLF"             { return CRLF; }
  "WHITE_SPACE"      { return WHITE_SPACE; }


}

[^] { return BAD_CHARACTER; }
