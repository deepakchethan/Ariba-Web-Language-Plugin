package com.github.deepakchethan.aribaweblanguageplugin.language;

import com.intellij.psi.tree.IElementType;

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

%state DOC_TYPE
%state COMMENT
%state START_TAG_NAME
%state END_TAG_NAME
%state BEFORE_TAG_ATTRIBUTES
%state TAG_ATTRIBUTES
%state ATTRIBUTE_VALUE_START
%state ATTRIBUTE_VALUE_DQ
%state ATTRIBUTE_VALUE_SQ
%state PROCESSING_INSTRUCTION
%state TAG_CHARACTERS
%state C_COMMENT_START
%state C_COMMENT_END
/* IMPORTANT! number of states should not exceed 16. See JspHighlightingLexer. */

ALPHA=[:letter:]
DIGIT=[0-9]
WHITE_SPACE_CHARS=[ \n\r\t\f\u2028\u2029\u0085]+

TAG_NAME=({ALPHA}|"_"|":")({ALPHA}|{DIGIT}|"_"|":"|"."|"-")*
ATTRIBUTE_NAME=([^ \n\r\t\f\"\'<>/=])+

DTD_REF= "\"" [^\"]* "\"" | "'" [^']* "'"
DOCTYPE= "<!" (D|d)(O|o)(C|c)(T|t)(Y|y)(P|p)(E|e)
HTML= (H|h)(T|t)(M|m)(L|l)
PUBLIC= (P|p)(U|u)(B|b)(L|l)(I|i)(C|c)
END_COMMENT="-->"

CONDITIONAL_COMMENT_CONDITION=({ALPHA})({ALPHA}|{WHITE_SPACE_CHARS}|{DIGIT}|"."|"("|")"|"|"|"!"|"&")*
%%

<YYINITIAL> "<?" { yybegin(PROCESSING_INSTRUCTION); return AWLElementType.AWL_PI_START; }
<PROCESSING_INSTRUCTION> "?"? ">" { yybegin(YYINITIAL); return AWLElementType.AWL_PI_END; }
<PROCESSING_INSTRUCTION> ([^\?\>] | (\?[^\>]))* { return AWLElementType.AWL_PI_TARGET; }

<YYINITIAL> {DOCTYPE} { yybegin(DOC_TYPE); return AWLElementType.AWL_DOCTYPE_START; }
<DOC_TYPE> {HTML} { return AWLElementType.AWL_NAME; }
<DOC_TYPE> {PUBLIC} { return AWLElementType.AWL_DOCTYPE_PUBLIC; }
<DOC_TYPE> {DTD_REF} { return AWLElementType.AWL_ATTRIBUTE_VALUE_TOKEN;}
<DOC_TYPE> ">" { yybegin(YYINITIAL); return AWLElementType.AWL_DOCTYPE_END; }
<YYINITIAL> {WHITE_SPACE_CHARS} { return AWLElementType.AWL_REAL_WHITE_SPACE; }
<DOC_TYPE,TAG_ATTRIBUTES,ATTRIBUTE_VALUE_START,PROCESSING_INSTRUCTION, START_TAG_NAME, END_TAG_NAME, TAG_CHARACTERS> {WHITE_SPACE_CHARS} { return AWLElementType.AWL_WHITE_SPACE; }
<YYINITIAL> "<" {TAG_NAME} { yybegin(START_TAG_NAME); yypushback(yylength()); }
<START_TAG_NAME, TAG_CHARACTERS> "<" { return AWLElementType.AWL_START_TAG_START; }

<YYINITIAL> "</" {TAG_NAME} { yybegin(END_TAG_NAME); yypushback(yylength()); }
<YYINITIAL, END_TAG_NAME> "</" { return AWLElementType.AWL_END_TAG_START; }

<YYINITIAL> "<!--" { yybegin(COMMENT); return AWLElementType.AWL_COMMENT_START; }
<COMMENT> "[" { yybegin(C_COMMENT_START); return AWLElementType.AWL_CONDITIONAL_COMMENT_START; }
<COMMENT> "<![" { yybegin(C_COMMENT_END); return AWLElementType.AWL_CONDITIONAL_COMMENT_END_START; }
<COMMENT> {END_COMMENT} | "<!-->" { yybegin(YYINITIAL); return AWLElementType.AWL_COMMENT_END; }
<COMMENT> "<!--" { return AWLElementType.AWL_BAD_CHARACTER; }
<COMMENT> "<!--->" | "--!>" { yybegin(YYINITIAL); return AWLElementType.AWL_BAD_CHARACTER; }
<COMMENT> ">" {
  // according to HTML spec (http://www.w3.org/html/wg/drafts/html/master/syntax.html#comments)
  // comments should start with <!-- and end with -->. The comment <!--> is not valid, but should terminate
  // comment token. Please note that it's not true for AWL (http://www.w3.org/TR/REC-AWL/#sec-comments)
  int loc = getTokenStart();
  char prev = zzBuffer.charAt(loc - 1);
  char prevPrev = zzBuffer.charAt(loc - 2);
  if (prev == '-' && prevPrev == '-') {
    yybegin(YYINITIAL); return AWLElementType.AWL_BAD_CHARACTER;
  }
  return AWLElementType.AWL_COMMENT_CHARACTERS;
}
<COMMENT> [^] { return AWLElementType.AWL_COMMENT_CHARACTERS; }

<C_COMMENT_START,C_COMMENT_END> {CONDITIONAL_COMMENT_CONDITION} { return AWLElementType.AWL_COMMENT_CHARACTERS; }
<C_COMMENT_START> [^] { yybegin(COMMENT); return AWLElementType.AWL_COMMENT_CHARACTERS; }
<C_COMMENT_START> "]>" { yybegin(COMMENT); return AWLElementType.AWL_CONDITIONAL_COMMENT_START_END; }
<C_COMMENT_START,C_COMMENT_END> {END_COMMENT} { yybegin(YYINITIAL); return AWLElementType.AWL_COMMENT_END; }
<C_COMMENT_END> "]" { yybegin(COMMENT); return AWLElementType.AWL_CONDITIONAL_COMMENT_END; }
<C_COMMENT_END> [^] { yybegin(COMMENT); return AWLElementType.AWL_COMMENT_CHARACTERS; }

<YYINITIAL> \\\$ {
  return AWLElementType.AWL_DATA_CHARACTERS;
}

<START_TAG_NAME, END_TAG_NAME> {TAG_NAME} { yybegin(BEFORE_TAG_ATTRIBUTES); return AWLElementType.AWL_NAME; }

<BEFORE_TAG_ATTRIBUTES, TAG_ATTRIBUTES, TAG_CHARACTERS> ">" { yybegin(YYINITIAL); return AWLElementType.AWL_TAG_END; }
<BEFORE_TAG_ATTRIBUTES, TAG_ATTRIBUTES, TAG_CHARACTERS> "/>" { yybegin(YYINITIAL); return AWLElementType.AWL_EMPTY_ELEMENT_END; }
<BEFORE_TAG_ATTRIBUTES> {WHITE_SPACE_CHARS} { yybegin(TAG_ATTRIBUTES); return AWLElementType.AWL_WHITE_SPACE;}
<TAG_ATTRIBUTES> {ATTRIBUTE_NAME} { return AWLElementType.AWL_ATTRIBUTE_NAME; }
<TAG_ATTRIBUTES> "=" { yybegin(ATTRIBUTE_VALUE_START); return AWLElementType.AWL_EQ; }
<BEFORE_TAG_ATTRIBUTES, TAG_ATTRIBUTES, START_TAG_NAME, END_TAG_NAME> [^] { yybegin(YYINITIAL); yypushback(1); break; }

<TAG_CHARACTERS> [^] { return AWLElementType.AWL_TAG_CHARACTERS; }

<ATTRIBUTE_VALUE_START> ">" { yybegin(YYINITIAL); return AWLElementType.AWL_TAG_END; }
<ATTRIBUTE_VALUE_START> "/>" { yybegin(YYINITIAL); return AWLElementType.AWL_EMPTY_ELEMENT_END; }

<ATTRIBUTE_VALUE_START> [^ \n\r\t\f'\"\>]([^ \n\r\t\f\>]|(\/[^\>]))* { yybegin(TAG_ATTRIBUTES); return AWLElementType.AWL_ATTRIBUTE_VALUE_TOKEN; }
<ATTRIBUTE_VALUE_START> "\"" { yybegin(ATTRIBUTE_VALUE_DQ); return AWLElementType.AWL_ATTRIBUTE_VALUE_START_DELIMITER; }
<ATTRIBUTE_VALUE_START> "'" { yybegin(ATTRIBUTE_VALUE_SQ); return AWLElementType.AWL_ATTRIBUTE_VALUE_START_DELIMITER; }

<ATTRIBUTE_VALUE_DQ> {
  "\"" { yybegin(TAG_ATTRIBUTES); return AWLElementType.AWL_ATTRIBUTE_VALUE_END_DELIMITER; }
  \\\$ { return AWLElementType.AWL_ATTRIBUTE_VALUE_TOKEN; }
  [^] { return AWLElementType.AWL_ATTRIBUTE_VALUE_TOKEN;}
}

<ATTRIBUTE_VALUE_SQ> {
  "'" { yybegin(TAG_ATTRIBUTES); return AWLElementType.AWL_ATTRIBUTE_VALUE_END_DELIMITER; }
  \\\$ { return AWLElementType.AWL_ATTRIBUTE_VALUE_TOKEN; }
  [^] { return AWLElementType.AWL_ATTRIBUTE_VALUE_TOKEN;}
}

"&lt;" |
"&gt;" |
"&apos;" |
"&quot;" |
"&nbsp;" |
"&amp;" |
"&#"{DIGIT}+";" |
"&#"[xX]({DIGIT}|[a-fA-F])+";" { return AWLElementType.AWL_CHAR_ENTITY_REF; }
"&"{TAG_NAME}";" { return AWLElementType.AWL_ENTITY_REF_TOKEN; }

<YYINITIAL> ([^<&\$# \n\r\t\f]|(\\\$)|(\\#))* { return AWLElementType.AWL_DATA_CHARACTERS; }
<YYINITIAL> [^] { return AWLElementType.AWL_DATA_CHARACTERS; }
[^] { return AWLElementType.AWL_BAD_CHARACTER; }