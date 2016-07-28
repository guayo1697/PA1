/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */
    // Max size of string constants
    static int MAX_STR_CONST = 1025;
    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();
    private int curr_lineno = 1;
    int white = 0;
    int comentario = 0;
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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int ESTOFERROR = 3;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int STR = 4;
	private final int COMMENTLINEAL = 2;
	private final int yy_state_dtrans[] = {
		0,
		216,
		218,
		220,
		222
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NOT_ACCEPT,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NOT_ACCEPT,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NOT_ACCEPT,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NOT_ACCEPT,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NOT_ACCEPT,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NOT_ACCEPT,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NOT_ACCEPT,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NOT_ACCEPT,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NOT_ACCEPT,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NOT_ACCEPT,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NOT_ACCEPT,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NOT_ACCEPT,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NOT_ACCEPT,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NOT_ACCEPT,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NOT_ACCEPT,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NOT_ACCEPT,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NOT_ACCEPT,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NOT_ACCEPT,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NOT_ACCEPT,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NOT_ACCEPT,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NO_ANCHOR,
		/* 175 */ YY_NOT_ACCEPT,
		/* 176 */ YY_NO_ANCHOR,
		/* 177 */ YY_NO_ANCHOR,
		/* 178 */ YY_NOT_ACCEPT,
		/* 179 */ YY_NO_ANCHOR,
		/* 180 */ YY_NO_ANCHOR,
		/* 181 */ YY_NOT_ACCEPT,
		/* 182 */ YY_NO_ANCHOR,
		/* 183 */ YY_NO_ANCHOR,
		/* 184 */ YY_NOT_ACCEPT,
		/* 185 */ YY_NO_ANCHOR,
		/* 186 */ YY_NO_ANCHOR,
		/* 187 */ YY_NOT_ACCEPT,
		/* 188 */ YY_NO_ANCHOR,
		/* 189 */ YY_NO_ANCHOR,
		/* 190 */ YY_NOT_ACCEPT,
		/* 191 */ YY_NO_ANCHOR,
		/* 192 */ YY_NOT_ACCEPT,
		/* 193 */ YY_NO_ANCHOR,
		/* 194 */ YY_NOT_ACCEPT,
		/* 195 */ YY_NO_ANCHOR,
		/* 196 */ YY_NOT_ACCEPT,
		/* 197 */ YY_NO_ANCHOR,
		/* 198 */ YY_NOT_ACCEPT,
		/* 199 */ YY_NO_ANCHOR,
		/* 200 */ YY_NOT_ACCEPT,
		/* 201 */ YY_NO_ANCHOR,
		/* 202 */ YY_NOT_ACCEPT,
		/* 203 */ YY_NO_ANCHOR,
		/* 204 */ YY_NOT_ACCEPT,
		/* 205 */ YY_NO_ANCHOR,
		/* 206 */ YY_NOT_ACCEPT,
		/* 207 */ YY_NO_ANCHOR,
		/* 208 */ YY_NOT_ACCEPT,
		/* 209 */ YY_NO_ANCHOR,
		/* 210 */ YY_NOT_ACCEPT,
		/* 211 */ YY_NO_ANCHOR,
		/* 212 */ YY_NOT_ACCEPT,
		/* 213 */ YY_NO_ANCHOR,
		/* 214 */ YY_NOT_ACCEPT,
		/* 215 */ YY_NO_ANCHOR,
		/* 216 */ YY_NOT_ACCEPT,
		/* 217 */ YY_NO_ANCHOR,
		/* 218 */ YY_NOT_ACCEPT,
		/* 219 */ YY_NO_ANCHOR,
		/* 220 */ YY_NOT_ACCEPT,
		/* 221 */ YY_NO_ANCHOR,
		/* 222 */ YY_NOT_ACCEPT,
		/* 223 */ YY_NOT_ACCEPT,
		/* 224 */ YY_NO_ANCHOR,
		/* 225 */ YY_NOT_ACCEPT,
		/* 226 */ YY_NO_ANCHOR,
		/* 227 */ YY_NOT_ACCEPT,
		/* 228 */ YY_NO_ANCHOR,
		/* 229 */ YY_NOT_ACCEPT,
		/* 230 */ YY_NO_ANCHOR,
		/* 231 */ YY_NO_ANCHOR,
		/* 232 */ YY_NO_ANCHOR,
		/* 233 */ YY_NO_ANCHOR,
		/* 234 */ YY_NOT_ACCEPT,
		/* 235 */ YY_NO_ANCHOR,
		/* 236 */ YY_NO_ANCHOR,
		/* 237 */ YY_NO_ANCHOR,
		/* 238 */ YY_NO_ANCHOR,
		/* 239 */ YY_NO_ANCHOR,
		/* 240 */ YY_NO_ANCHOR,
		/* 241 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"6,65:7,13,8,4,65,10,9,65:18,7,65,63,65:5,2,5,3,42,45,1,33,41,50:10,46,47,43" +
",11,12,65,34,17,62,14,62,19,25,62,28,26,62:2,15,62,27,30,31,62,21,18,29,22," +
"62,32,62:3,65,23,65:2,61,65,51,64,53,40,54,24,52,55,36,52:2,56,52,44,39,57," +
"52,58,37,20,59,38,60,52:3,48,16,49,35,65,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,242,
"0,1,2,3,4,1:4,5,1,6,7,1:5,8,1:5,9,1:5,10,1,11,1:3,12,1,13,14,1,14,1,15,1:5," +
"13,1:5,16,1,17,1:9,18,19,20,21,1,11,22,20,1,20,1,20,1,14,1,15,13,20:2,13,20" +
",3,23,24,25,26,20,27,13,20,13,20,13,28,20:2,13,29,13:2,30,31,32,13:3,33,13," +
"34,13:2,20,13,35,36,37,20,13,20,38,39,40,13,41,42,43,44,45,46,47,48,49,50,5" +
"1,52,53,54,55,26,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,12,73,7" +
"4,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,9" +
"9,100,101,102,103,104,105,106,107,108,109,110,14,111,15,112,34,113,114,115," +
"29,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133," +
"134,135,136,137,138,139,140,141,142,143,144,145,146")[0];

	private int yy_nxt[][] = unpackFromString(147,66,
"1,2,3,4,5,6,7,8:2,67,7,9,7,10,11,224,68,69:2,233,12,69:2,7,70,90,109,236,69" +
",237,121,238,239,13,14,15,91,110:2,122,110,16,17,18,128,19,20,21,22,23,24,1" +
"10:2,132,135,110,138,141,110:2,144,7,69,25,110,7,-1:67,26,-1:67,27,-1:67,28" +
",-1:72,29,-1:67,69,127,89,131,69:5,-1,69:9,-1:3,69:5,-1:3,69,-1:5,69,131,69" +
":4,127,69:6,-1,69,-1:15,110:2,139,110:4,147,110,142,110:4,150,110:4,-1:3,11" +
"0:5,-1:3,110,-1:5,110:5,150,110:2,147,110:4,-1,110,-1:2,34,-1:9,35,-1:104,2" +
"4,-1:31,113,172,169,175,75,-1:5,178,-1,181,75,184,-1,37,-1:3,178,169,-1,184" +
",-1:11,172,-1:2,175,181,-1:4,37,-1:21,181,-1:11,181,-1:26,181,-1:26,40,-1:1" +
"4,40,-1:25,40,-1:22,110:2,-1,110:6,-1,110:9,-1:3,110:5,-1:3,110,-1:5,110:13" +
",-1,110,-1:17,46,-1,46,-1:18,46,-1:44,47,-1:2,47,-1:34,47,-1:12,55:3,-1,55," +
"-1,55:16,-1,55:39,-1,55:2,-1:4,59,-1:2,60,-1:12,61,-1:2,223,62,-1:19,63,-1:" +
"18,64,65,-1:11,8,-1:70,225,30,120,126,130,-1:4,31:2,71,32,133,-1,136,-1:5,7" +
"1,126,-1,136,-1:4,32,-1:6,120,-1:2,130,133,225,-1:23,69:2,-1,69:6,-1,69:9,-" +
"1:3,69:5,-1:3,69,-1:5,69:13,-1,69,-1:15,110:2,92,153,110:5,145,110:2,111,11" +
"0:6,-1:3,111,110:4,-1:3,110,-1:5,110,153,110:11,-1,110,-1:15,69:2,181,69:6," +
"-1,69:4,231,69:4,-1:3,69:5,-1:3,69,-1:5,69:5,231,69:7,-1,69,-1:17,157,227,1" +
"60,-1:18,160,-1:13,227,-1:28,69:2,71,69:6,-1,69:2,123,69:6,-1:3,123,69:4,-1" +
":3,69,-1:5,69:13,-1,69,-1:15,110:2,72,110:6,-1,112:2,110,94,110:5,-1:3,110," +
"241,110:3,-1:3,94,-1:5,110:13,-1,110,-1:16,198:2,-1:6,198,-1:32,198,-1:23,1" +
"10:2,181,110:6,-1,110:4,193,110:4,-1:3,110:5,-1:3,110,-1:5,110:5,193,110:7," +
"-1,110,-1:17,104,-1,46,47,-1,210,-1:15,46,-1:16,47,-1:3,210,-1:23,212,-1:9," +
"212,-1:9,212,-1:34,53,-1:76,36,-1:3,75,-1:8,75,163,-1:8,163,-1:40,69:2,72,6" +
"9:6,-1,93:2,69,73,69:5,-1:3,69:5,-1:3,73,-1:5,69:13,-1,69,-1:15,42,43,100,-" +
"1,202,115,-1:7,44,-1:3,40,-1:5,202,-1:6,44,-1:8,42,115,-1,43,40,-1:24,210,-" +
"1:4,210,-1:36,210,-1:23,160,-1,160,-1:18,160,-1:42,69:2,33,69:6,-1,74:2,69:" +
"7,-1:3,69:5,-1:3,69,-1:5,69:13,-1,69,-1:15,110:2,33,110:6,-1,95:2,110:7,-1:" +
"3,110:5,-1:3,110,-1:5,110:13,-1,110,-1:17,187:2,-1:33,187,-1:28,69:2,227,23" +
"5,69:5,-1,69:9,-1:3,69:5,-1:3,69,-1:5,69,235,69:11,-1,69,-1:15,110:2,148,11" +
"0:2,156,110:3,-1,110:9,-1:3,110:3,159,110,-1:3,110,-1:5,110:4,156,110:8,-1," +
"110,-1:17,75,-1:3,75,-1:8,75,-1:2,37,-1:27,37,-1:19,69:2,160,69,152,69:4,-1" +
",69:9,-1:3,69,152,69:3,-1:3,69,-1:5,69:13,-1,69,-1:15,110,162,89,165,110:5," +
"-1,110:9,-1:3,110:5,-1:3,110,-1:5,110,165,110:4,162,110:6,-1,110,-1:17,190," +
"-1:2,175,-1:6,178,-1:9,178,-1:17,175,-1:25,69:2,75,69:3,96,69:2,-1,69:5,96," +
"69:3,-1:3,69:5,-1:3,69,-1:5,69:13,-1,69,-1:15,110,168,234,110,171,110:4,-1," +
"110:9,-1:3,110,171,110:3,-1:3,110,-1:5,110:6,168,110:6,-1,110,-1:17,184,-1:" +
"13,184,-1:8,184,-1:40,69:2,163,69:6,-1,69:6,155,69:2,-1:3,69:3,155,69,-1:3," +
"69,-1:5,69:13,-1,69,-1:15,110:2,108,110:2,174,110:3,-1,110:6,177,110:2,-1:3" +
",110:3,177,110,-1:3,110,-1:5,110:4,174,110:8,-1,110,-1:17,194,-1:2,175,-1:2" +
",196:2,-1:30,175,-1:4,196,-1:20,69:2,187,161,69:5,-1,69:9,-1:3,69:5,-1:3,69" +
",-1:5,69,161,69:11,-1,69,-1:15,110:2,229,110:6,-1,110:6,180,110:2,-1:3,110:" +
"3,180,110,-1:3,110,-1:5,110:13,-1,110,-1:17,196,-1:5,196:2,-1:35,196,-1:20," +
"69:2,37,69:6,-1,69:8,76,-1:3,69:5,-1:3,69,-1:5,69:10,76,69:2,-1,69,-1:15,11" +
"0:2,154,110:6,-1,110:4,183,110:4,-1:3,110:5,-1:3,110,-1:5,110:5,183,110:7,-" +
"1,110,-1:15,69:2,175,69:2,164,69:3,-1,69:9,-1:3,69:5,-1:3,69,-1:5,69:4,164," +
"69:8,-1,69,-1:15,110:2,196,110:5,186,196,110:9,-1:3,110:5,-1:3,110,-1:5,110" +
":9,186,110:3,-1,110,-1:17,37,-1:15,37,-1:27,37,-1:19,69:2,178,69:6,-1,69:2," +
"170,69:6,-1:3,170,69:4,-1:3,69,-1:5,69:13,-1,69,-1:15,110:2,175,110:2,189,1" +
"10:3,-1,110:9,-1:3,110:5,-1:3,110,-1:5,110:4,189,110:8,-1,110,-1:17,175,-1:" +
"2,175,-1:34,175,-1:25,69:2,77,69:2,98,69:3,-1,69:9,-1:3,69:5,-1:3,69,-1:5,6" +
"9:4,98,69:8,-1,69,-1:15,110,191,198,110:6,198,110:9,-1:3,110:5,-1:3,110,-1:" +
"5,110:6,191,110:6,-1,110,-1:17,178,-1:9,178,-1:9,178,-1:43,69:2,40,69:6,-1," +
"69:7,78,69,-1:3,69:5,-1:3,69,-1:5,69:7,78,69:5,-1,69,-1:15,110:2,37,110:6,-" +
"1,110:8,97,-1:3,110:5,-1:3,110,-1:5,110:10,97,110:2,-1,110,-1:17,39,-1,202," +
"77,-1:17,202,-1:16,77,-1:25,69:2,79,69:2,125,69:3,-1,69:9,-1:3,69:5,-1:3,69" +
",-1:5,69:4,125,69:8,-1,69,-1:15,110:2,-1,110:3,38,110:2,-1,110:9,-1:3,110:5" +
",-1:3,110,-1:5,110:13,-1,110,-1:17,77,-1:2,77,-1:34,77,-1:25,101,69,42,69:6" +
",-1,69:9,-1:3,69:5,-1:3,69,-1:5,69:3,101,69:9,-1,69,-1:15,110:2,227,232,110" +
":5,-1,110:9,-1:3,110:5,-1:3,110,-1:5,110,232,110:11,-1,110,-1:15,69:2,44,69" +
":6,-1,69:3,118,69:5,-1:3,69:5,-1:3,118,-1:5,69:13,-1,69,-1:15,110:2,160,110" +
",195,110:4,-1,110:9,-1:3,110,195,110:3,-1:3,110,-1:5,110:13,-1,110,-1:17,41" +
",-1,202,79,-1:17,202,-1:16,79,-1:25,69,102,81,69:6,-1,69:9,-1:3,69:5,-1:3,6" +
"9,-1:5,69:6,102,69:6,-1,69,-1:15,110:2,169,110,197,110:4,-1,110:9,-1:3,110," +
"197,110:3,-1:3,110,-1:5,110:13,-1,110,-1:17,79,-1:2,79,-1:34,79,-1:25,69,17" +
"9,204,69:6,-1,69:9,-1:3,69:5,-1:3,69,-1:5,69:6,179,69:6,-1,69,-1:15,110:2,1" +
"87,199,110:5,-1,110:9,-1:3,110:5,-1:3,110,-1:5,110,199,110:11,-1,110,-1:15," +
"42,-1,80,-1,202,-1:18,202,-1:15,42,-1:26,69:2,46,69,84,69:4,-1,69:9,-1:3,69" +
",84,69:3,-1:3,69,-1:5,69:13,-1,69,-1:15,110:2,75,110:3,124,110:2,-1,110:5,1" +
"24,110:3,-1:3,110:5,-1:3,110,-1:5,110:13,-1,110,-1:17,44,-1:10,44,-1:16,44," +
"-1:35,69:2,210,69:4,182,69,-1,69:9,-1:3,69:5,-1:3,69,-1:5,69:8,182,69:4,-1," +
"69,-1:15,110:2,163,110:6,-1,110:6,201,110:2,-1:3,110:3,201,110,-1:3,110,-1:" +
"5,110:13,-1,110,-1:16,204:2,-1:39,204,-1:23,69:2,47,69:2,85,69:3,-1,69:9,-1" +
":3,69:5,-1:3,69,-1:5,69:4,85,69:8,-1,69,-1:15,110:2,200,110:6,-1,110:6,203," +
"110:2,-1:3,110:3,203,110,-1:3,110,-1:5,110:13,-1,110,-1:17,206,-1:2,206,-1:" +
"34,206,-1:25,69:2,212,69:6,-1,69:2,185,69:6,-1:3,185,69:4,-1:3,69,-1:5,69:1" +
"3,-1,69,-1:15,110:2,178,110:6,-1,110:2,205,110:6,-1:3,205,110:4,-1:3,110,-1" +
":5,110:13,-1,110,-1:16,81,40,-1:14,40,-1:24,81,40,-1:22,69:2,214,69:3,188,6" +
"9:2,-1,69:5,188,69:3,-1:3,69:5,-1:3,69,-1:5,69:13,-1,69,-1:15,110:2,45,110:" +
"2,83,110:3,45,110:9,-1:3,110:5,-1:3,110,-1:5,110:4,83,110:8,-1,110,-1:15,42" +
",-1,42,-1:36,42,-1:26,69:2,50,69,87,69:4,-1,69:9,-1:3,69,87,69:3,-1:3,69,-1" +
":5,69:13,-1,69,-1:15,110:2,44,110:6,-1,110:3,103,110:5,-1:3,110:5,-1:3,103," +
"-1:5,110:13,-1,110,-1:16,204,82,-1:10,44,-1:16,44,-1:11,204,-1:23,110:2,208" +
",110,207,110:4,208,110:9,-1:3,110,207,110:3,-1:3,110,-1:5,110:13,-1,110,-1:" +
"15,42,-1,79,-1:2,79,-1:33,42,79,-1:25,110:2,206,110:2,209,110:3,-1,110:9,-1" +
":3,110:5,-1:3,110,-1:5,110:4,209,110:8,-1,110,-1:17,45,-1:2,45,-1:3,45,-1:3" +
",44,-1:16,44,-1:9,45,-1:25,110:2,77,110:2,114,110:3,-1,110:9,-1:3,110:5,-1:" +
"3,110,-1:5,110:4,114,110:8,-1,110,-1:17,45,-1:2,45,-1:3,45,-1:30,45,-1:25,1" +
"10:2,79,110:2,129,110:3,-1,110:9,-1:3,110:5,-1:3,110,-1:5,110:4,129,110:8,-" +
"1,110,-1:17,208,-1,208,-1:4,208,-1:13,208,-1:42,116,110,42,110:6,-1,110:9,-" +
"1:3,110:5,-1:3,110,-1:5,110:3,116,110:9,-1,110,-1:16,81:2,-1:39,81,-1:23,11" +
"0:2,40,110:6,-1,110:7,99,110,-1:3,110:5,-1:3,110,-1:5,110:7,99,110:5,-1,110" +
",-1:15,110,117,81,110:6,-1,110:9,-1:3,110:5,-1:3,110,-1:5,110:6,117,110:6,-" +
"1,110,-1:15,110,213,204,110:6,-1,110:9,-1:3,110:5,-1:3,110,-1:5,110:6,213,1" +
"10:6,-1,110,-1:15,110:2,48,110:2,86,110:3,48,110:9,-1:3,110:5,-1:3,110,-1:5" +
",110:4,86,110:8,-1,110,-1:17,48,-1:2,48,-1:3,48,-1:30,48,-1:25,110:2,210,11" +
"0:4,215,110,-1,110:9,-1:3,110:5,-1:3,110,-1:5,110:8,215,110:4,-1,110,-1:15," +
"110:2,46,110,119,110:4,-1,110:9,-1:3,110,119,110:3,-1:3,110,-1:5,110:13,-1," +
"110,-1:17,214,-1:3,214,-1:8,214,-1:50,110:2,47,110:2,105,110:3,-1,110:9,-1:" +
"3,110:5,-1:3,110,-1:5,110:4,105,110:8,-1,110,-1:17,50,-1,50,-1:18,50,-1:42," +
"110:2,212,110:6,-1,110:2,219,110:6,-1:3,219,110:4,-1:3,110,-1:5,110:13,-1,1" +
"10,-1,1,51,88,107,52,51:4,-1,51:56,-1:14,110:2,-1,110:6,-1,110:9,-1:3,110:4" +
",49,-1:3,110,-1:5,110:13,-1,110,-1,1,51:3,54,51:4,-1,51:56,-1:14,110:2,214," +
"110:3,221,110:2,-1,110:5,221,110:3,-1:3,110:5,-1:3,110,-1:5,110:13,-1,110,-" +
"1,1,7:3,-1,7:4,-1,7:56,-1:14,110:2,50,110,106,110:4,-1,110:9,-1:3,110,106,1" +
"10:3,-1:3,110,-1:5,110:13,-1,110,-1,1,55:3,56,55,7,55:16,57,55:39,58,55:2,-" +
"1:44,66,-1:35,69:2,108,69:2,134,69:3,-1,69:6,137,69:2,-1:3,69:3,137,69,-1:3" +
",69,-1:5,69:4,134,69:8,-1,69,-1:17,166,227,169,-1:18,169,-1:13,227,-1:28,11" +
"0:2,-1,110:6,-1,110:9,-1:3,217,110:4,-1:3,110,-1:5,110:13,-1,110,-1:17,202," +
"-1,202,-1:18,202,-1:42,69:2,169,69,158,69:4,-1,69:9,-1:3,69,158,69:3,-1:3,6" +
"9,-1:5,69:13,-1,69,-1:17,200,-1:13,200,-1:8,200,-1:40,69:2,200,69:6,-1,69:6" +
",167,69:2,-1:3,69:3,167,69,-1:3,69,-1:5,69:13,-1,69,-1:15,69:2,206,69:2,176" +
",69:3,-1,69:9,-1:3,69:5,-1:3,69,-1:5,69:4,176,69:8,-1,69,-1:15,110:2,202,11" +
"0,211,110:4,-1,110:9,-1:3,110,211,110:3,-1:3,110,-1:5,110:13,-1,110,-1:15,6" +
"9,228,234,69,140,69:4,-1,69:9,-1:3,69,140,69:3,-1:3,69,-1:5,69:6,228,69:6,-" +
"1,69,-1:17,192,187,169,-1:18,169,-1:13,187,-1:28,69:2,202,69,173,69:4,-1,69" +
":9,-1:3,69,173,69:3,-1:3,69,-1:5,69:13,-1,69,-1:15,69:2,148,69:2,143,69:3,-" +
"1,69:9,-1:3,69:5,-1:3,69,-1:5,69:4,143,69:8,-1,69,-1:15,69:2,151,69:6,-1,69" +
":4,146,69:4,-1:3,69:5,-1:3,69,-1:5,69:5,146,69:7,-1,69,-1:15,69:2,229,69:6," +
"-1,69:6,230,69:2,-1:3,69:3,230,69,-1:3,69,-1:5,69:13,-1,69,-1:15,69:2,154,6" +
"9:6,-1,69:4,149,69:4,-1:3,69:5,-1:3,69,-1:5,69:5,149,69:7,-1,69,-1:15,110:2" +
",-1,110:6,-1,110:9,-1:3,110:3,226,110,-1:3,110,-1:5,110:13,-1,110,-1:15,110" +
":2,-1,110:6,-1,110:9,-1:3,110:2,240,110:2,-1:3,110,-1:5,110:13,-1,110,-1");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

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
		case COMMENT:
	    	yybegin(ESTOFERROR);	
	   		return new Symbol(TokenConstants.ERROR, "Se alcanzo EOF en comentario!");
	   	case STR:
	   		yybegin(ESTOFERROR);
	   		return new Symbol(TokenConstants.ERROR, "Se alcanzo EOF en String");
	   case ESTOFERROR:
	   break;
    }
    return new Symbol(TokenConstants.EOF);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{return new Symbol(TokenConstants.MINUS); }
					case -3:
						break;
					case 3:
						{return new Symbol(TokenConstants.LPAREN); }
					case -4:
						break;
					case 4:
						{return new Symbol(TokenConstants.MULT); }
					case -5:
						break;
					case 5:
						{curr_lineno++;}
					case -6:
						break;
					case 6:
						{return new Symbol(TokenConstants.RPAREN); }
					case -7:
						break;
					case 7:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -8:
						break;
					case 8:
						{white++;}
					case -9:
						break;
					case 9:
						{return new Symbol(TokenConstants.EQ); }
					case -10:
						break;
					case 10:
						{}
					case -11:
						break;
					case 11:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -12:
						break;
					case 12:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -13:
						break;
					case 13:
						{return new Symbol(TokenConstants.DOT); }
					case -14:
						break;
					case 14:
						{return new Symbol(TokenConstants.AT); }
					case -15:
						break;
					case 15:
						{return new Symbol(TokenConstants.NEG); }
					case -16:
						break;
					case 16:
						{return new Symbol(TokenConstants.DIV); }
					case -17:
						break;
					case 17:
						{return new Symbol(TokenConstants.PLUS); }
					case -18:
						break;
					case 18:
						{return new Symbol(TokenConstants.LT); }
					case -19:
						break;
					case 19:
						{return new Symbol(TokenConstants.COMMA); }
					case -20:
						break;
					case 20:
						{return new Symbol(TokenConstants.COLON); }
					case -21:
						break;
					case 21:
						{return new Symbol(TokenConstants.SEMI); }
					case -22:
						break;
					case 22:
						{return new Symbol(TokenConstants.LBRACE); }
					case -23:
						break;
					case 23:
						{return new Symbol(TokenConstants.RBRACE); }
					case -24:
						break;
					case 24:
						{	AbstractSymbol inte = AbstractTable.inttable.addString(yytext());
							return new Symbol(TokenConstants.INT_CONST,inte); }
					case -25:
						break;
					case 25:
						{yybegin(STR); string_buf.setLength(0); }
					case -26:
						break;
					case 26:
						{yybegin(COMMENTLINEAL);}
					case -27:
						break;
					case 27:
						{yybegin(COMMENT);comentario++;}
					case -28:
						break;
					case 28:
						{return new Symbol(TokenConstants.ERROR, "Unmatched *)");}
					case -29:
						break;
					case 29:
						{return new Symbol(TokenConstants.DARROW); }
					case -30:
						break;
					case 30:
						{return new Symbol(TokenConstants.FI);}
					case -31:
						break;
					case 31:
						{return new Symbol(TokenConstants.IF);}
					case -32:
						break;
					case 32:
						{return new Symbol(TokenConstants.IN);}
					case -33:
						break;
					case 33:
						{return new Symbol(TokenConstants.OF);}
					case -34:
						break;
					case 34:
						{return new Symbol(TokenConstants.ASSIGN); }
					case -35:
						break;
					case 35:
						{return new Symbol(TokenConstants.LE); }
					case -36:
						break;
					case 36:
						{return new Symbol(TokenConstants.LET);}
					case -37:
						break;
					case 37:
						{return new Symbol(TokenConstants.NEW);}
					case -38:
						break;
					case 38:
						{return new Symbol(TokenConstants.NOT); }
					case -39:
						break;
					case 39:
						{return new Symbol(TokenConstants.CASE);}
					case -40:
						break;
					case 40:
						{return new Symbol(TokenConstants.LOOP);}
					case -41:
						break;
					case 41:
						{return new Symbol(TokenConstants.ELSE);}
					case -42:
						break;
					case 42:
						{return new Symbol(TokenConstants.ESAC);}
					case -43:
						break;
					case 43:
						{return new Symbol(TokenConstants.POOL);}
					case -44:
						break;
					case 44:
						{return new Symbol(TokenConstants.THEN);}
					case -45:
						break;
					case 45:
						{return new Symbol(TokenConstants.BOOL_CONST, "true"); }
					case -46:
						break;
					case 46:
						{return new Symbol(TokenConstants.CLASS);}
					case -47:
						break;
					case 47:
						{return new Symbol(TokenConstants.WHILE);}
					case -48:
						break;
					case 48:
						{return new Symbol(TokenConstants.BOOL_CONST, "false"); }
					case -49:
						break;
					case 49:
						{return new Symbol(TokenConstants.ISVOID);}
					case -50:
						break;
					case 50:
						{return new Symbol(TokenConstants.INHERITS);}
					case -51:
						break;
					case 51:
						{}
					case -52:
						break;
					case 52:
						{curr_lineno++;}
					case -53:
						break;
					case 53:
						{if (--comentario == 0) {yybegin(YYINITIAL);}}
					case -54:
						break;
					case 54:
						{curr_lineno++; yybegin(YYINITIAL);}
					case -55:
						break;
					case 55:
						{string_buf.append(yytext());}
					case -56:
						break;
					case 56:
						{yybegin(YYINITIAL); string_buf.setLength(0); return new Symbol(TokenConstants.ERROR, "Undetermined string constant");}
					case -57:
						break;
					case 57:
						{}
					case -58:
						break;
					case 58:
						{yybegin(YYINITIAL); return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(string_buf.toString()));}
					case -59:
						break;
					case 59:
						{string_buf.append("\n");}
					case -60:
						break;
					case 60:
						{string_buf.append("\\");}
					case -61:
						break;
					case 61:
						{string_buf.append("\t");}
					case -62:
						break;
					case 62:
						{string_buf.append("\f");}
					case -63:
						break;
					case 63:
						{string_buf.append("\n");}
					case -64:
						break;
					case 64:
						{string_buf.append("\"");}
					case -65:
						break;
					case 65:
						{string_buf.append("\b");}
					case -66:
						break;
					case 66:
						{string_buf.append("\\n");}
					case -67:
						break;
					case 68:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -68:
						break;
					case 69:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -69:
						break;
					case 70:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -70:
						break;
					case 71:
						{return new Symbol(TokenConstants.FI);}
					case -71:
						break;
					case 72:
						{return new Symbol(TokenConstants.IF);}
					case -72:
						break;
					case 73:
						{return new Symbol(TokenConstants.IN);}
					case -73:
						break;
					case 74:
						{return new Symbol(TokenConstants.OF);}
					case -74:
						break;
					case 75:
						{return new Symbol(TokenConstants.LET);}
					case -75:
						break;
					case 76:
						{return new Symbol(TokenConstants.NEW);}
					case -76:
						break;
					case 77:
						{return new Symbol(TokenConstants.CASE);}
					case -77:
						break;
					case 78:
						{return new Symbol(TokenConstants.LOOP);}
					case -78:
						break;
					case 79:
						{return new Symbol(TokenConstants.ELSE);}
					case -79:
						break;
					case 80:
						{return new Symbol(TokenConstants.ESAC);}
					case -80:
						break;
					case 81:
						{return new Symbol(TokenConstants.POOL);}
					case -81:
						break;
					case 82:
						{return new Symbol(TokenConstants.THEN);}
					case -82:
						break;
					case 83:
						{return new Symbol(TokenConstants.BOOL_CONST, "true"); }
					case -83:
						break;
					case 84:
						{return new Symbol(TokenConstants.CLASS);}
					case -84:
						break;
					case 85:
						{return new Symbol(TokenConstants.WHILE);}
					case -85:
						break;
					case 86:
						{return new Symbol(TokenConstants.BOOL_CONST, "false"); }
					case -86:
						break;
					case 87:
						{return new Symbol(TokenConstants.INHERITS);}
					case -87:
						break;
					case 88:
						{}
					case -88:
						break;
					case 90:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -89:
						break;
					case 91:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -90:
						break;
					case 92:
						{return new Symbol(TokenConstants.FI);}
					case -91:
						break;
					case 93:
						{return new Symbol(TokenConstants.IF);}
					case -92:
						break;
					case 94:
						{return new Symbol(TokenConstants.IN);}
					case -93:
						break;
					case 95:
						{return new Symbol(TokenConstants.OF);}
					case -94:
						break;
					case 96:
						{return new Symbol(TokenConstants.LET);}
					case -95:
						break;
					case 97:
						{return new Symbol(TokenConstants.NEW);}
					case -96:
						break;
					case 98:
						{return new Symbol(TokenConstants.CASE);}
					case -97:
						break;
					case 99:
						{return new Symbol(TokenConstants.LOOP);}
					case -98:
						break;
					case 100:
						{return new Symbol(TokenConstants.ELSE);}
					case -99:
						break;
					case 101:
						{return new Symbol(TokenConstants.ESAC);}
					case -100:
						break;
					case 102:
						{return new Symbol(TokenConstants.POOL);}
					case -101:
						break;
					case 103:
						{return new Symbol(TokenConstants.THEN);}
					case -102:
						break;
					case 104:
						{return new Symbol(TokenConstants.CLASS);}
					case -103:
						break;
					case 105:
						{return new Symbol(TokenConstants.WHILE);}
					case -104:
						break;
					case 106:
						{return new Symbol(TokenConstants.INHERITS);}
					case -105:
						break;
					case 107:
						{}
					case -106:
						break;
					case 109:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -107:
						break;
					case 110:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -108:
						break;
					case 111:
						{return new Symbol(TokenConstants.FI);}
					case -109:
						break;
					case 112:
						{return new Symbol(TokenConstants.IF);}
					case -110:
						break;
					case 113:
						{return new Symbol(TokenConstants.LET);}
					case -111:
						break;
					case 114:
						{return new Symbol(TokenConstants.CASE);}
					case -112:
						break;
					case 115:
						{return new Symbol(TokenConstants.ELSE);}
					case -113:
						break;
					case 116:
						{return new Symbol(TokenConstants.ESAC);}
					case -114:
						break;
					case 117:
						{return new Symbol(TokenConstants.POOL);}
					case -115:
						break;
					case 118:
						{return new Symbol(TokenConstants.THEN);}
					case -116:
						break;
					case 119:
						{return new Symbol(TokenConstants.CLASS);}
					case -117:
						break;
					case 121:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -118:
						break;
					case 122:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -119:
						break;
					case 123:
						{return new Symbol(TokenConstants.FI);}
					case -120:
						break;
					case 124:
						{return new Symbol(TokenConstants.LET);}
					case -121:
						break;
					case 125:
						{return new Symbol(TokenConstants.ELSE);}
					case -122:
						break;
					case 127:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -123:
						break;
					case 128:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -124:
						break;
					case 129:
						{return new Symbol(TokenConstants.ELSE);}
					case -125:
						break;
					case 131:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -126:
						break;
					case 132:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -127:
						break;
					case 134:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -128:
						break;
					case 135:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -129:
						break;
					case 137:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -130:
						break;
					case 138:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -131:
						break;
					case 140:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -132:
						break;
					case 141:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -133:
						break;
					case 143:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -134:
						break;
					case 144:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -135:
						break;
					case 146:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -136:
						break;
					case 147:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -137:
						break;
					case 149:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -138:
						break;
					case 150:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -139:
						break;
					case 152:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -140:
						break;
					case 153:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -141:
						break;
					case 155:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -142:
						break;
					case 156:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -143:
						break;
					case 158:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -144:
						break;
					case 159:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -145:
						break;
					case 161:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -146:
						break;
					case 162:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -147:
						break;
					case 164:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -148:
						break;
					case 165:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -149:
						break;
					case 167:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -150:
						break;
					case 168:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -151:
						break;
					case 170:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -152:
						break;
					case 171:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -153:
						break;
					case 173:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -154:
						break;
					case 174:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -155:
						break;
					case 176:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -156:
						break;
					case 177:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -157:
						break;
					case 179:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -158:
						break;
					case 180:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -159:
						break;
					case 182:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -160:
						break;
					case 183:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -161:
						break;
					case 185:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -162:
						break;
					case 186:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -163:
						break;
					case 188:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -164:
						break;
					case 189:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -165:
						break;
					case 191:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -166:
						break;
					case 193:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -167:
						break;
					case 195:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -168:
						break;
					case 197:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -169:
						break;
					case 199:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -170:
						break;
					case 201:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -171:
						break;
					case 203:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -172:
						break;
					case 205:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -173:
						break;
					case 207:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -174:
						break;
					case 209:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -175:
						break;
					case 211:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -176:
						break;
					case 213:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -177:
						break;
					case 215:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -178:
						break;
					case 217:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -179:
						break;
					case 219:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -180:
						break;
					case 221:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -181:
						break;
					case 224:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -182:
						break;
					case 226:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -183:
						break;
					case 228:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -184:
						break;
					case 230:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -185:
						break;
					case 231:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -186:
						break;
					case 232:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -187:
						break;
					case 233:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -188:
						break;
					case 235:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -189:
						break;
					case 236:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -190:
						break;
					case 237:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -191:
						break;
					case 238:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -192:
						break;
					case 239:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -193:
						break;
					case 240:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -194:
						break;
					case 241:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -195:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
