/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;

%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

    // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    private int curr_lineno = 1;
    int white = 1;
    int var_count = 1;
    int const_count = 1;
    int get_curr_lineno() {
	return curr_lineno;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
	return filename;
    }
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
		case STRING:
		return new Symbol(TokenConstants.ERROR, "Se alcanzo EOF en string!");
	    case COMENT:
	   	return new Symbol(TokenConstants.ERROR, "Se alcanzo EOF en comentario!");
	   case EOF:
	   break;
    }
    return new Symbol(TokenConstants.EOF);
%eofval}

%class CoolLexer
%cup
%state COMENT, STRING, EOF

%%

<YYINITIAL>"=>"			{ /* Sample lexical rule for "=>" arrow.
                                     Further lexical rules should be defined
                                     here, after the last %% separator */
                                  return new Symbol(TokenConstants.DARROW); }
<YYINITIAL>\b 			{}  
<YYINITIAL>[" "]|[\t]           {white++;}                                
<YYINITIAL>\n           {curr_lineno++;}
<YYINITIAL>\f 			{}
<YYINITIAL>["c" | "C"]["l" | "L"]["a" | "A"]["s | S"]["s | S"]	{return new Symbol(TokenConstants.CLASS,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>["e" | "E"]["l" | "L"]["s" | "S"]["e" | "E"]			{return new Symbol(TokenConstants.ELSE,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>["t"]["\r" | "\R"]["\u" | "\U"]["\e" | "\E"]       {return new Symbol(TokenConstants.BOOL_CONST,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>["f"]["\a" | "\A"]["\l" | "\L"]["\s" | "\S"]["\e" | "\E"]      {return new Symbol(TokenConstants.BOOL_CONST,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>["f" | "F"]["i" | "I"]	{return new Symbol(TokenConstants.FI,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>["i" | "I"]["f" | "F"]	{return new Symbol(TokenConstants.IF,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>["i" | "I"]["n" | "N"]	{return new Symbol(TokenConstants.IN,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>["i" | "I"]["n" | "N"]["h" | "H"]["e" | "E"]["r" | "R"]["i" | "I"]["t" | "T"]["s" | "S"]	{return new Symbol(TokenConstants.INHERITS,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>["l" | "L"]["e" | "E"]["t" | "T"]			{return new Symbol(TokenConstants.LET,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>["l" | "L"]["o" | "O"]["o" | "O"]["p" | "P"]			{return new Symbol(TokenConstants.LOOP,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>["p" | "P"]["o" | "O"]["o" | "O"]["l" | "L"]			{return new Symbol(TokenConstants.POOL,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>["t" | "T"]["h" | "H"]["e" | "E"]["n" | "N"]			{return new Symbol(TokenConstants.THEN,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>["w" | "W"]["h" | "H"]["i" | "I"]["l" | "L"]["e" | "E"]			{return new Symbol(TokenConstants.WHILE,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>["c" | "C"]["a" | "A"]["s" | "S"]["e" | "E"]			{return new Symbol(TokenConstants.CASE,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>["e" | "E"]["s" | "S"]["a" | "A"]["c" | "C"]			{return new Symbol(TokenConstants.ESAC,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>["n" | "N"]["e" | "E"]["w" | "W"]			{return new Symbol(TokenConstants.NEW,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>["o" | "O"]["f" | "F"]	{return new Symbol(TokenConstants.OF,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>"."          {return new Symbol(TokenConstants.DOT,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>"@"          {return new Symbol(TokenConstants.AT,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>"~"          {return new Symbol(TokenConstants.NEG,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>["i"]["s"]["v"]["o"]["i"]["d"]	{return new Symbol(TokenConstants.ISVOID,new IdSymbol(yytext(), yytext().length(), white++));}
<YYINITIAL>"/"          {return new Symbol(TokenConstants.DIV,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>"*"          {return new Symbol(TokenConstants.MULT,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>"+"          {return new Symbol(TokenConstants.PLUS,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>"-"          {return new Symbol(TokenConstants.MINUS,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>"<="         {return new Symbol(TokenConstants.LE,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>"<"          {return new Symbol(TokenConstants.LT,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>"="          {return new Symbol(TokenConstants.EQ,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>"not"        {return new Symbol(TokenConstants.NOT,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>"<-"         {return new Symbol(TokenConstants.ASSIGN,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>","          {return new Symbol(TokenConstants.COMMA,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>":"          {return new Symbol(TokenConstants.COLON,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>";"          {return new Symbol(TokenConstants.SEMI,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>[\{] 		{return new Symbol(TokenConstants.LBRACE,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>[\}] 		{return new Symbol(TokenConstants.RBRACE,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>[\(]  		{return new Symbol(TokenConstants.LPAREN,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>[\)]  		{return new Symbol(TokenConstants.RPAREN,new IdSymbol(yytext(), yytext().length(), white++)); }
<YYINITIAL>[0-9]+       {return new Symbol(TokenConstants.INT_CONST,new IdSymbol(yytext(), yytext().length(), const_count++)); }
<YYINITIAL>[a-z][a-zA-Z0-9]*         {return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
<YYINITIAL>[\"][^\0.*]*[\"]  {
									String token = yytext().substring(1, yytext().length() - 1);
									int l = token.length();
									return new Symbol(TokenConstants.STR_CONST, new StringSymbol(token,l,0));
								 }
<YYINITIAL>[A-Z][a-zA-Z0-9\_]*       {return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
<YYINITIAL>error		{return new Symbol(TokenConstants.error);}


.                               { /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
