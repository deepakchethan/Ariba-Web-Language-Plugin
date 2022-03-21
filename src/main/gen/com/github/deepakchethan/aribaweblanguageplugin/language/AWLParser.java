// This is a generated file. Not intended for manual editing.
package com.github.deepakchethan.aribaweblanguageplugin.language;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.deepakchethan.aribaweblanguageplugin.language.psi.AWLTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class AWLParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return awl_file(b, l + 1);
  }

  /* ********************************************************** */
  // lower-case-alphabet | higher-case-alphabet
  static boolean alphabet(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "alphabet")) return false;
    boolean r;
    r = lower_case_alphabet(b, l + 1);
    if (!r) r = higher_case_alphabet(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // attr-empty | attr-unquoted | attr-single-quoted | attr-double-quoted
  static boolean attr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr")) return false;
    boolean r;
    r = attr_empty(b, l + 1);
    if (!r) r = attr_unquoted(b, l + 1);
    if (!r) r = attr_single_quoted(b, l + 1);
    if (!r) r = attr_double_quoted(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // attr-name + equals + ws* attr-double-quoted-value
  static boolean attr_double_quoted(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_double_quoted")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_double_quoted_0(b, l + 1);
    r = r && attr_double_quoted_1(b, l + 1);
    r = r && attr_double_quoted_2(b, l + 1);
    r = r && attr_double_quoted_value(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // attr-name +
  private static boolean attr_double_quoted_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_double_quoted_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_name(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!attr_name(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_double_quoted_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // equals +
  private static boolean attr_double_quoted_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_double_quoted_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = equals(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!equals(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_double_quoted_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // ws*
  private static boolean attr_double_quoted_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_double_quoted_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ws(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_double_quoted_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // "\"" + attr-unquoted-value +  "\""
  static boolean attr_double_quoted_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_double_quoted_value")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_double_quoted_value_0(b, l + 1);
    r = r && attr_double_quoted_value_1(b, l + 1);
    r = r && consumeToken(b, "\"");
    exit_section_(b, m, null, r);
    return r;
  }

  // "\"" +
  private static boolean attr_double_quoted_value_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_double_quoted_value_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "\"");
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, "\"")) break;
      if (!empty_element_parsed_guard_(b, "attr_double_quoted_value_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // attr-unquoted-value +
  private static boolean attr_double_quoted_value_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_double_quoted_value_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_unquoted_value(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!attr_unquoted_value(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_double_quoted_value_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // empty
  static boolean attr_empty(PsiBuilder b, int l) {
    return empty(b, l + 1);
  }

  /* ********************************************************** */
  // (ws+ attr)*
  static boolean attr_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_list")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attr_list_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_list", c)) break;
    }
    return true;
  }

  // ws+ attr
  private static boolean attr_list_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_list_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_list_0_0(b, l + 1);
    r = r && attr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws+
  private static boolean attr_list_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_list_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ws(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!ws(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_list_0_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (digit | alphabet | special-character)+ ws* | empty
  static boolean attr_name(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_name")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_name_0(b, l + 1);
    if (!r) r = empty(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (digit | alphabet | special-character)+ ws*
  private static boolean attr_name_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_name_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_name_0_0(b, l + 1);
    r = r && attr_name_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (digit | alphabet | special-character)+
  private static boolean attr_name_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_name_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_name_0_0_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!attr_name_0_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_name_0_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // digit | alphabet | special-character
  private static boolean attr_name_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_name_0_0_0")) return false;
    boolean r;
    r = digit(b, l + 1);
    if (!r) r = alphabet(b, l + 1);
    if (!r) r = special_character(b, l + 1);
    return r;
  }

  // ws*
  private static boolean attr_name_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_name_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ws(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_name_0_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // attr-name + equals + ws* attr-single-quoted-value
  static boolean attr_single_quoted(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_single_quoted")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_single_quoted_0(b, l + 1);
    r = r && attr_single_quoted_1(b, l + 1);
    r = r && attr_single_quoted_2(b, l + 1);
    r = r && attr_single_quoted_value(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // attr-name +
  private static boolean attr_single_quoted_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_single_quoted_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_name(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!attr_name(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_single_quoted_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // equals +
  private static boolean attr_single_quoted_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_single_quoted_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = equals(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!equals(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_single_quoted_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // ws*
  private static boolean attr_single_quoted_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_single_quoted_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ws(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_single_quoted_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // "\'" + attr-unquoted-value +  "\'"
  static boolean attr_single_quoted_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_single_quoted_value")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_single_quoted_value_0(b, l + 1);
    r = r && attr_single_quoted_value_1(b, l + 1);
    r = r && consumeToken(b, "\'");
    exit_section_(b, m, null, r);
    return r;
  }

  // "\'" +
  private static boolean attr_single_quoted_value_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_single_quoted_value_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "\'");
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, "\'")) break;
      if (!empty_element_parsed_guard_(b, "attr_single_quoted_value_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // attr-unquoted-value +
  private static boolean attr_single_quoted_value_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_single_quoted_value_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_unquoted_value(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!attr_unquoted_value(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_single_quoted_value_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // attr-name + equals + ws* attr-unquoted-value
  static boolean attr_unquoted(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_unquoted")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_unquoted_0(b, l + 1);
    r = r && attr_unquoted_1(b, l + 1);
    r = r && attr_unquoted_2(b, l + 1);
    r = r && attr_unquoted_value(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // attr-name +
  private static boolean attr_unquoted_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_unquoted_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_name(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!attr_name(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_unquoted_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // equals +
  private static boolean attr_unquoted_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_unquoted_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = equals(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!equals(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_unquoted_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // ws*
  private static boolean attr_unquoted_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_unquoted_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ws(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_unquoted_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (digit | alphabet | special-character)+
  static boolean attr_unquoted_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_unquoted_value")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attr_unquoted_value_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!attr_unquoted_value_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attr_unquoted_value", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // digit | alphabet | special-character
  private static boolean attr_unquoted_value_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attr_unquoted_value_0")) return false;
    boolean r;
    r = digit(b, l + 1);
    if (!r) r = alphabet(b, l + 1);
    if (!r) r = special_character(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean awl_file(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "awl_file")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "awl_file", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
  static boolean digit(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "digit")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "0");
    if (!r) r = consumeToken(b, "1");
    if (!r) r = consumeToken(b, "2");
    if (!r) r = consumeToken(b, "3");
    if (!r) r = consumeToken(b, "4");
    if (!r) r = consumeToken(b, "5");
    if (!r) r = consumeToken(b, "6");
    if (!r) r = consumeToken(b, "7");
    if (!r) r = consumeToken(b, "8");
    if (!r) r = consumeToken(b, "9");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ''
  static boolean empty(PsiBuilder b, int l) {
    return consumeToken(b, "");
  }

  /* ********************************************************** */
  // "="
  static boolean equals(PsiBuilder b, int l) {
    return consumeToken(b, "=");
  }

  /* ********************************************************** */
  // "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" | "K" | "L" | "M" | "N" | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z"
  static boolean higher_case_alphabet(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "higher_case_alphabet")) return false;
    boolean r;
    r = consumeToken(b, "A");
    if (!r) r = consumeToken(b, "B");
    if (!r) r = consumeToken(b, "C");
    if (!r) r = consumeToken(b, "D");
    if (!r) r = consumeToken(b, "E");
    if (!r) r = consumeToken(b, "F");
    if (!r) r = consumeToken(b, "G");
    if (!r) r = consumeToken(b, "H");
    if (!r) r = consumeToken(b, "I");
    if (!r) r = consumeToken(b, "J");
    if (!r) r = consumeToken(b, "K");
    if (!r) r = consumeToken(b, "L");
    if (!r) r = consumeToken(b, "M");
    if (!r) r = consumeToken(b, "N");
    if (!r) r = consumeToken(b, "O");
    if (!r) r = consumeToken(b, "P");
    if (!r) r = consumeToken(b, "Q");
    if (!r) r = consumeToken(b, "R");
    if (!r) r = consumeToken(b, "S");
    if (!r) r = consumeToken(b, "T");
    if (!r) r = consumeToken(b, "U");
    if (!r) r = consumeToken(b, "V");
    if (!r) r = consumeToken(b, "W");
    if (!r) r = consumeToken(b, "X");
    if (!r) r = consumeToken(b, "Y");
    if (!r) r = consumeToken(b, "Z");
    return r;
  }

  /* ********************************************************** */
  // tag | COMMENT | CRLF
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = tag(b, l + 1);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, CRLF);
    return r;
  }

  /* ********************************************************** */
  // "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" | "i" | "j" | "k" | "l" | "m" | "n" | "o" | "p" | "q" | "r" | "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z"
  static boolean lower_case_alphabet(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lower_case_alphabet")) return false;
    boolean r;
    r = consumeToken(b, "a");
    if (!r) r = consumeToken(b, "b");
    if (!r) r = consumeToken(b, "c");
    if (!r) r = consumeToken(b, "d");
    if (!r) r = consumeToken(b, "e");
    if (!r) r = consumeToken(b, "f");
    if (!r) r = consumeToken(b, "g");
    if (!r) r = consumeToken(b, "h");
    if (!r) r = consumeToken(b, "i");
    if (!r) r = consumeToken(b, "j");
    if (!r) r = consumeToken(b, "k");
    if (!r) r = consumeToken(b, "l");
    if (!r) r = consumeToken(b, "m");
    if (!r) r = consumeToken(b, "n");
    if (!r) r = consumeToken(b, "o");
    if (!r) r = consumeToken(b, "p");
    if (!r) r = consumeToken(b, "q");
    if (!r) r = consumeToken(b, "r");
    if (!r) r = consumeToken(b, "s");
    if (!r) r = consumeToken(b, "t");
    if (!r) r = consumeToken(b, "u");
    if (!r) r = consumeToken(b, "v");
    if (!r) r = consumeToken(b, "w");
    if (!r) r = consumeToken(b, "x");
    if (!r) r = consumeToken(b, "y");
    if (!r) r = consumeToken(b, "z");
    return r;
  }

  /* ********************************************************** */
  // "*" | "$"
  static boolean special_character(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "special_character")) return false;
    boolean r;
    r = consumeToken(b, "*");
    if (!r) r = consumeToken(b, "$");
    return r;
  }

  /* ********************************************************** */
  // tag-open | tag-close | tag-empty
  public static boolean tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TAG, "<tag>");
    r = tag_open(b, l + 1);
    if (!r) r = tag_close(b, l + 1);
    if (!r) r = tag_empty(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '</' tag-name ws* '>'
  static boolean tag_close(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_close")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "</");
    r = r && tag_name(b, l + 1);
    r = r && tag_close_2(b, l + 1);
    r = r && consumeToken(b, ">");
    exit_section_(b, m, null, r);
    return r;
  }

  // ws*
  private static boolean tag_close_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_close_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ws(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tag_close_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // '<' tag-name ws* attr-list? ws* '/>'
  static boolean tag_empty(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_empty")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "<");
    r = r && tag_name(b, l + 1);
    r = r && tag_empty_2(b, l + 1);
    r = r && tag_empty_3(b, l + 1);
    r = r && tag_empty_4(b, l + 1);
    r = r && consumeToken(b, "/>");
    exit_section_(b, m, null, r);
    return r;
  }

  // ws*
  private static boolean tag_empty_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_empty_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ws(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tag_empty_2", c)) break;
    }
    return true;
  }

  // attr-list?
  private static boolean tag_empty_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_empty_3")) return false;
    attr_list(b, l + 1);
    return true;
  }

  // ws*
  private static boolean tag_empty_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_empty_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ws(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tag_empty_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (alphabet | digit)+
  static boolean tag_name(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_name")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = tag_name_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!tag_name_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tag_name", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // alphabet | digit
  private static boolean tag_name_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_name_0")) return false;
    boolean r;
    r = alphabet(b, l + 1);
    if (!r) r = digit(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // '<' tag-name ws* attr-list? ws* '>'
  static boolean tag_open(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_open")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "<");
    r = r && tag_name(b, l + 1);
    r = r && tag_open_2(b, l + 1);
    r = r && tag_open_3(b, l + 1);
    r = r && tag_open_4(b, l + 1);
    r = r && consumeToken(b, ">");
    exit_section_(b, m, null, r);
    return r;
  }

  // ws*
  private static boolean tag_open_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_open_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ws(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tag_open_2", c)) break;
    }
    return true;
  }

  // attr-list?
  private static boolean tag_open_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_open_3")) return false;
    attr_list(b, l + 1);
    return true;
  }

  // ws*
  private static boolean tag_open_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_open_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ws(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tag_open_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // WHITE_SPACE
  static boolean ws(PsiBuilder b, int l) {
    return consumeToken(b, WHITE_SPACE);
  }

}
