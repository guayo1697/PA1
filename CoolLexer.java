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
	private final int STRING = 2;
	private final int COMENT = 1;
	private final int YYINITIAL = 0;
	private final int EOF = 3;
	private final int yy_state_dtrans[] = {
		0,
		198,
		198,
		198
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
		/* 49 */ YY_NOT_ACCEPT,
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
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NOT_ACCEPT,
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
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NOT_ACCEPT,
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
		/* 102 */ YY_NOT_ACCEPT,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NOT_ACCEPT,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NOT_ACCEPT,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NOT_ACCEPT,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NOT_ACCEPT,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NOT_ACCEPT,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NOT_ACCEPT,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NOT_ACCEPT,
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
		/* 174 */ YY_NOT_ACCEPT,
		/* 175 */ YY_NO_ANCHOR,
		/* 176 */ YY_NOT_ACCEPT,
		/* 177 */ YY_NO_ANCHOR,
		/* 178 */ YY_NOT_ACCEPT,
		/* 179 */ YY_NO_ANCHOR,
		/* 180 */ YY_NOT_ACCEPT,
		/* 181 */ YY_NO_ANCHOR,
		/* 182 */ YY_NOT_ACCEPT,
		/* 183 */ YY_NO_ANCHOR,
		/* 184 */ YY_NOT_ACCEPT,
		/* 185 */ YY_NO_ANCHOR,
		/* 186 */ YY_NOT_ACCEPT,
		/* 187 */ YY_NO_ANCHOR,
		/* 188 */ YY_NOT_ACCEPT,
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
		/* 200 */ YY_NO_ANCHOR,
		/* 201 */ YY_NO_ANCHOR,
		/* 202 */ YY_NOT_ACCEPT,
		/* 203 */ YY_NOT_ACCEPT,
		/* 204 */ YY_NO_ANCHOR,
		/* 205 */ YY_NOT_ACCEPT,
		/* 206 */ YY_NO_ANCHOR,
		/* 207 */ YY_NO_ANCHOR,
		/* 208 */ YY_NO_ANCHOR,
		/* 209 */ YY_NO_ANCHOR,
		/* 210 */ YY_NO_ANCHOR,
		/* 211 */ YY_NO_ANCHOR,
		/* 212 */ YY_NOT_ACCEPT,
		/* 213 */ YY_NO_ANCHOR,
		/* 214 */ YY_NO_ANCHOR,
		/* 215 */ YY_NO_ANCHOR,
		/* 216 */ YY_NO_ANCHOR,
		/* 217 */ YY_NO_ANCHOR,
		/* 218 */ YY_NO_ANCHOR,
		/* 219 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"63,64:7,3,5,6,64,7,61,64:18,4,64,60,64:5,46,47,36,37,41,38,27,35,48:10,42,4" +
"3,39,1,2,64,28,11,59,8,59,13,19,59,22,20,59:2,9,59,21,24,25,59,15,12,23,16," +
"59,26,59:3,64,17,64:2,62,64,49,50,51,34,52,18,50,53,30,50:2,54,50,40,33,55," +
"50,56,31,14,57,32,58,50:3,44,10,45,29,64,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,220,
"0,1,2,1:2,3,1:2,4,5,1:7,6,1:7,7,1,8,1,9,1:3,10,11,1,12,13,1,14,1:4,15,1:2,1" +
"2,1,16,3,1,17,18,1,9,19,17,1,17,20,21,1,14,17,21,12,1,17,12,17,22,10,23,24," +
"25,17,26,12,27,12,1,17:2,12:2,17:2,12:2,28,29,12:3,17,21,12:2,17,12:2,30,31" +
",32,17,12,17,33,34,35,12,36,37,38,39,40,41,42,43,44,45,46,47,25,48,49,50,51" +
",52,53,54,55,56,57,58,10,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75" +
",76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,27,94,95,96,97,98,99" +
",100,101,102,103,104,21,105,14,106,20,107,108,109,15,110,111,112,113,114,11" +
"5,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,1" +
"34,135,136")[0];

	private int yy_nxt[][] = unpackFromString(137,65,
"1,2,3,4,5,51,6,7,8,201,50,52:2,211,9,52:2,3,53,73,91,214,52,215,103,216,217" +
",10,11,12,74,92:2,104,92,13,14,15,16,17,110,18,19,20,21,22,23,24,25,92:2,11" +
"4,117,92,120,123,92:2,126,52,72,-1,3:3,-1:67,26,-1:66,27,-1:4,49,27,71,90,1" +
"02,-1:4,28:2,54,29,108,-1,112,-1:5,54,90,-1,112,-1:6,29,-1:8,71,-1:2,102,10" +
"8,49,-1:14,202,-1:3,52,109,202,113,52:5,-1,52:9,-1:3,52:5,-1:5,52,-1:7,52,1" +
"13,52:4,109,52:5,-1:2,52,-1:6,118,-1:3,92:2,118,92:4,129,92,121,92:4,132,92" +
":4,-1:3,92:5,-1:5,92,-1:7,92:5,132,92:2,129,92:3,-1:6,31,-1:36,32,-1:74,25," +
"-1:20,34,-1:5,34,139,142,145,58,-1:5,148,-1,151,58,154,-1,35,-1:3,148,142,-" +
"1,154,-1:15,139,-1:2,145,151,-1:4,35,-1:10,151,-1:5,151,-1:11,151,-1:30,151" +
",-1:12,136:26,-1,136:8,-1,136:23,33,136:2,-1,136,-1:4,37,-1:3,38,39,37,-1,1" +
"84,60,-1:7,40,-1:3,41,-1:5,184,-1:8,40,-1:10,38,60,-1,39,41,-1:17,92:2,-1,9" +
"2:6,-1,92:9,-1:3,92:5,-1:5,92,-1:7,92:12,-1:9,44,-1:5,44,-1,67,45,-1,192,-1" +
":15,67,-1:20,45,-1:3,192,-1:12,45,-1:5,45,-1:2,45,-1:38,45,-1:16,194,-1:5,1" +
"94,-1:9,194,-1:9,194,-1:38,157,-1:5,157,203,142,-1:18,142,-1:17,203,-1:23,5" +
"2:2,-1,52:6,-1,52:9,-1:3,52:5,-1:5,52,-1:7,52:12,-1:2,52,-1:6,75,-1:3,92:2," +
"75,135,92:5,124,92:2,93,92:6,-1:3,93,92:4,-1:5,92,-1:7,92,135,92:10,-1:9,15" +
"1,-1:3,52:2,151,52:6,-1,52:4,208,52:4,-1:3,52:5,-1:5,52,-1:7,52:5,208,52:6," +
"-1:2,52,-1:6,192,-1:5,192,-1:4,192,-1:40,192,-1:12,67,-1:5,67,-1,67,-1:18,6" +
"7,-1:37,160,-1:5,160,-1,160,-1:18,160,-1:37,54,-1:3,52:2,54,52:6,-1,52:2,10" +
"5,52:6,-1:3,105,52:4,-1:5,52,-1:7,52:12,-1:2,52,-1:6,55,-1:3,92:2,55,92:6,-" +
"1,94:2,92,77,92:5,-1:3,92,219,92:3,-1:5,77,-1:7,92:12,-1:9,180,-1:4,180:2,-" +
"1:6,180,-1:36,180,-1:14,151,-1:3,92:2,151,92:6,-1,92:4,173,92:4,-1:3,92:5,-" +
"1:5,92,-1:7,92:5,173,92:6,-1:9,41,-1:5,41,-1:14,41,-1:29,41,-1:13,163,-1:5," +
"163:2,-1:37,163,-1:19,55,-1:3,52:2,55,52:6,-1,76:2,52,56,52:5,-1:3,52:5,-1:" +
"5,56,-1:7,52:12,-1:2,52,-1:6,58,-1:5,58,-1:3,58,-1:8,58,-1:2,35,-1:31,35,-1" +
":10,30,-1:3,52:2,30,52:6,-1,57:2,52:7,-1:3,52:5,-1:5,52,-1:7,52:12,-1:2,52," +
"-1:6,30,-1:3,92:2,30,92:6,-1,78:2,92:7,-1:3,92:5,-1:5,92,-1:7,92:12,-1:9,16" +
"6,-1:5,166,-1:2,145,-1:6,148,-1:9,148,-1:21,145,-1:16,203,-1:3,52:2,203,213" +
",52:5,-1,52:9,-1:3,52:5,-1:5,52,-1:7,52,213,52:10,-1:2,52,-1:6,127,-1:3,92:" +
"2,127,92:2,138,92:3,-1,92:9,-1:3,92:3,141,92,-1:5,92,-1:7,92:4,138,92:7,-1:" +
"9,154,-1:5,154,-1:13,154,-1:8,154,-1:35,160,-1:3,52:2,160,52,134,52:4,-1,52" +
":9,-1:3,52,134,52:3,-1:5,52,-1:7,52:12,-1:2,52,-1:6,202,-1:3,92,144,202,147" +
",92:5,-1,92:9,-1:3,92:5,-1:5,92,-1:7,92,147,92:4,144,92:5,-1:9,79,-1:5,79,-" +
"1:3,58,-1:8,58,172,-1:8,172,-1:35,58,-1:3,52:2,58,52:3,95,52:2,-1,52:5,95,5" +
"2:3,-1:3,52:5,-1:5,52,-1:7,52:12,-1:2,52,-1:6,212,-1:3,92,150,212,92,153,92" +
":4,-1,92:9,-1:3,92,153,92:3,-1:5,92,-1:7,92:6,150,92:5,-1:9,176,-1:5,176,-1" +
":2,145,-1:2,178:2,-1:34,145,-1:4,178,-1:11,172,-1:3,52:2,172,52:6,-1,52:6,1" +
"37,52:2,-1:3,52:3,137,52,-1:5,52,-1:7,52:12,-1:2,52,-1:6,115,-1:3,92:2,115," +
"92:2,156,92:3,-1,92:6,159,92:2,-1:3,92:3,159,92,-1:5,92,-1:7,92:4,156,92:7," +
"-1:9,178,-1:5,178,-1:5,178:2,-1:39,178,-1:11,163,-1:3,52:2,163,143,52:5,-1," +
"52:9,-1:3,52:5,-1:5,52,-1:7,52,143,52:10,-1:2,52,-1:6,205,-1:3,92:2,205,92:" +
"6,-1,92:6,210,92:2,-1:3,92:3,210,92,-1:5,92,-1:7,92:12,-1:9,35,-1:3,52:2,35" +
",52:6,-1,52:8,59,-1:3,52:5,-1:5,52,-1:7,52:10,59,52,-1:2,52,-1:6,133,-1:3,9" +
"2:2,133,92:6,-1,92:4,162,92:4,-1:3,92:5,-1:5,92,-1:7,92:5,162,92:6,-1:9,35," +
"-1:5,35,-1:15,35,-1:31,35,-1:10,145,-1:3,52:2,145,52:2,146,52:3,-1,52:9,-1:" +
"3,52:5,-1:5,52,-1:7,52:4,146,52:7,-1:2,52,-1:6,178,-1:3,92:2,178,92:5,165,1" +
"78,92:9,-1:3,92:5,-1:5,92,-1:7,92:9,165,92:2,-1:9,145,-1:5,145,-1:2,145,-1:" +
"38,145,-1:16,148,-1:3,52:2,148,52:6,-1,52:2,152,52:6,-1:3,152,52:4,-1:5,52," +
"-1:7,52:12,-1:2,52,-1:6,145,-1:3,92:2,145,92:2,168,92:3,-1,92:9,-1:3,92:5,-" +
"1:5,92,-1:7,92:4,168,92:7,-1:9,148,-1:5,148,-1:9,148,-1:9,148,-1:38,42,-1:3" +
",52:2,42,52:2,86,52:3,-1,52:9,-1:3,52:5,-1:5,52,-1:7,52:4,86,52:7,-1:2,52,-" +
"1:6,180,-1:3,92,171,180,92:6,180,92:9,-1:3,92:5,-1:5,92,-1:7,92:6,171,92:5," +
"-1:9,41,-1:3,52:2,41,52:6,-1,52:7,64,52,-1:3,52:5,-1:5,52,-1:7,52:7,64,52:4" +
",-1:2,52,-1:6,35,-1:3,92:2,35,92:6,-1,92:8,80,-1:3,92:5,-1:5,92,-1:7,92:10," +
"80,92,-1:9,61,-1:3,38,-1,61,-1,184,-1:18,184,-1:19,38,-1:17,81,-1:3,52:2,81" +
",52:2,107,52:3,-1,52:9,-1:3,52:5,-1:5,52,-1:7,52:4,107,52:7,-1:2,52,-1:10,9" +
"2:2,-1,92:3,36,92:2,-1,92:9,-1:3,92:5,-1:5,92,-1:7,92:12,-1:9,81,-1:5,81,-1" +
":2,81,-1:38,81,-1:16,38,-1:3,82,52,38,52:6,-1,52:9,-1:3,52:5,-1:5,52,-1:7,5" +
"2:3,82,52:8,-1:2,52,-1:6,203,-1:3,92:2,203,209,92:5,-1,92:9,-1:3,92:5,-1:5," +
"92,-1:7,92,209,92:10,-1:9,40,-1:5,40,-1:10,40,-1:18,40,-1:28,40,-1:3,52:2,4" +
"0,52:6,-1,52:3,99,52:5,-1:3,52:5,-1:5,99,-1:7,52:12,-1:2,52,-1:6,160,-1:3,9" +
"2:2,160,92,175,92:4,-1,92:9,-1:3,92,175,92:3,-1:5,92,-1:7,92:12,-1:9,186,-1" +
":4,186:2,-1:43,186,-1:14,62,-1:3,52,83,62,52:6,-1,52:9,-1:3,52:5,-1:5,52,-1" +
":7,52:6,83,52:5,-1:2,52,-1:6,142,-1:3,92:2,142,92,177,92:4,-1,92:9,-1:3,92," +
"177,92:3,-1:5,92,-1:7,92:12,-1:9,188,-1:5,188,-1:2,188,-1:38,188,-1:16,186," +
"-1:3,52,161,186,52:6,-1,52:9,-1:3,52:5,-1:5,52,-1:7,52:6,161,52:5,-1:2,52,-" +
"1:6,163,-1:3,92:2,163,179,92:5,-1,92:9,-1:3,92:5,-1:5,92,-1:7,92,179,92:10," +
"-1:9,41,-1:4,62,41,-1:14,41,-1:28,62,41,-1:13,67,-1:3,52:2,67,52,87,52:4,-1" +
",52:9,-1:3,52,87,52:3,-1:5,52,-1:7,52:12,-1:2,52,-1:6,58,-1:3,92:2,58,92:3," +
"106,92:2,-1,92:5,106,92:3,-1:3,92:5,-1:5,92,-1:7,92:12,-1:9,96,-1:5,96,-1,1" +
"84,81,-1:17,184,-1:20,81,-1:16,192,-1:3,52:2,192,52:4,164,52,-1,52:9,-1:3,5" +
"2:5,-1:5,52,-1:7,52:8,164,52:3,-1:2,52,-1:6,172,-1:3,92:2,172,92:6,-1,92:6," +
"181,92:2,-1:3,92:3,181,92,-1:5,92,-1:7,92:12,-1:9,42,-1:5,42,-1:2,42,-1:38," +
"42,-1:16,45,-1:3,52:2,45,52:2,68,52:3,-1,52:9,-1:3,52:5,-1:5,52,-1:7,52:4,6" +
"8,52:7,-1:2,52,-1:6,148,-1:3,92:2,148,92:6,-1,92:2,185,92:6,-1:3,185,92:4,-" +
"1:5,92,-1:7,92:12,-1:9,38,-1:3,38,-1,38,-1:40,38,-1:17,194,-1:3,52:2,194,52" +
":6,-1,52:2,167,52:6,-1:3,167,52:4,-1:5,52,-1:7,52:12,-1:2,52,-1:6,43,-1:3,9" +
"2:2,43,92:2,66,92:3,43,92:9,-1:3,92:5,-1:5,92,-1:7,92:4,66,92:7,-1:9,63,-1:" +
"4,186,63,-1:10,40,-1:18,40,-1:13,186,-1:14,196,-1:3,52:2,196,52:3,170,52:2," +
"-1,52:5,170,52:3,-1:3,52:5,-1:5,52,-1:7,52:12,-1:2,52,-1:6,40,-1:3,92:2,40," +
"92:6,-1,92:3,84,92:5,-1:3,92:5,-1:5,84,-1:7,92:12,-1:9,65,-1:5,65,-1,184,42" +
",-1:17,184,-1:20,42,-1:16,48,-1:3,52:2,48,52,70,52:4,-1,52:9,-1:3,52,70,52:" +
"3,-1:5,52,-1:7,52:12,-1:2,52,-1:6,190,-1:3,92:2,190,92,187,92:4,190,92:9,-1" +
":3,92,187,92:3,-1:5,92,-1:7,92:12,-1:9,188,-1:3,92:2,188,92:2,189,92:3,-1,9" +
"2:9,-1:3,92:5,-1:5,92,-1:7,92:4,189,92:7,-1:9,81,-1:3,38,-1,81,-1:2,81,-1:3" +
"7,38,81,-1:16,42,-1:3,92:2,42,92:2,100,92:3,-1,92:9,-1:3,92:5,-1:5,92,-1:7," +
"92:4,100,92:7,-1:9,43,-1:5,43,-1:2,43,-1:3,43,-1:3,40,-1:18,40,-1:11,43,-1:" +
"16,81,-1:3,92:2,81,92:2,111,92:3,-1,92:9,-1:3,92:5,-1:5,92,-1:7,92:4,111,92" +
":7,-1:9,43,-1:5,43,-1:2,43,-1:3,43,-1:34,43,-1:16,38,-1:3,97,92,38,92:6,-1," +
"92:9,-1:3,92:5,-1:5,92,-1:7,92:3,97,92:8,-1:9,190,-1:5,190,-1,190,-1:4,190," +
"-1:13,190,-1:37,41,-1:3,92:2,41,92:6,-1,92:7,85,92,-1:3,92:5,-1:5,92,-1:7,9" +
"2:7,85,92:4,-1:9,62,-1:4,62:2,-1:43,62,-1:14,62,-1:3,92,98,62,92:6,-1,92:9," +
"-1:3,92:5,-1:5,92,-1:7,92:6,98,92:5,-1:9,186,-1:3,92,193,186,92:6,-1,92:9,-" +
"1:3,92:5,-1:5,92,-1:7,92:6,193,92:5,-1:9,46,-1:3,92:2,46,92:2,69,92:3,46,92" +
":9,-1:3,92:5,-1:5,92,-1:7,92:4,69,92:7,-1:9,192,-1:3,92:2,192,92:4,195,92,-" +
"1,92:9,-1:3,92:5,-1:5,92,-1:7,92:8,195,92:3,-1:9,46,-1:5,46,-1:2,46,-1:3,46" +
",-1:34,46,-1:16,67,-1:3,92:2,67,92,101,92:4,-1,92:9,-1:3,92,101,92:3,-1:5,9" +
"2,-1:7,92:12,-1:9,45,-1:3,92:2,45,92:2,88,92:3,-1,92:9,-1:3,92:5,-1:5,92,-1" +
":7,92:4,88,92:7,-1:9,196,-1:5,196,-1:3,196,-1:8,196,-1:45,194,-1:3,92:2,194" +
",92:6,-1,92:2,199,92:6,-1:3,199,92:4,-1:5,92,-1:7,92:12,-1:9,48,-1:5,48,-1," +
"48,-1:18,48,-1:41,92:2,-1,92:6,-1,92:9,-1:3,92:4,47,-1:5,92,-1:7,92:12,-1:5" +
",1,3:5,-1,3:54,-1,3:3,-1:4,196,-1:3,92:2,196,92:3,200,92:2,-1,92:5,200,92:3" +
",-1:3,92:5,-1:5,92,-1:7,92:12,-1:9,48,-1:3,92:2,48,92,89,92:4,-1,92:9,-1:3," +
"92,89,92:3,-1:5,92,-1:7,92:12,-1:9,115,-1:3,52:2,115,52:2,116,52:3,-1,52:6," +
"119,52:2,-1:3,52:3,119,52,-1:5,52,-1:7,52:4,116,52:7,-1:2,52,-1:6,169,-1:5," +
"169,203,160,-1:18,160,-1:17,203,-1:19,184,-1:5,184,-1,184,-1:18,184,-1:41,9" +
"2:2,-1,92:6,-1,92:9,-1:3,197,92:4,-1:5,92,-1:7,92:12,-1:9,182,-1:5,182,-1:1" +
"3,182,-1:8,182,-1:35,142,-1:3,52:2,142,52,140,52:4,-1,52:9,-1:3,52,140,52:3" +
",-1:5,52,-1:7,52:12,-1:2,52,-1:6,182,-1:3,52:2,182,52:6,-1,52:6,149,52:2,-1" +
":3,52:3,149,52,-1:5,52,-1:7,52:12,-1:2,52,-1:6,188,-1:3,52:2,188,52:2,158,5" +
"2:3,-1,52:9,-1:3,52:5,-1:5,52,-1:7,52:4,158,52:7,-1:2,52,-1:6,184,-1:3,92:2" +
",184,92,191,92:4,-1,92:9,-1:3,92,191,92:3,-1:5,92,-1:7,92:12,-1:9,182,-1:3," +
"92:2,182,92:6,-1,92:6,183,92:2,-1:3,92:3,183,92,-1:5,92,-1:7,92:12,-1:9,212" +
",-1:3,52,206,212,52,122,52:4,-1,52:9,-1:3,52,122,52:3,-1:5,52,-1:7,52:6,206" +
",52:5,-1:2,52,-1:6,174,-1:5,174,163,142,-1:18,142,-1:17,163,-1:19,184,-1:3," +
"52:2,184,52,155,52:4,-1,52:9,-1:3,52,155,52:3,-1:5,52,-1:7,52:12,-1:2,52,-1" +
":6,127,-1:3,52:2,127,52:2,125,52:3,-1,52:9,-1:3,52:5,-1:5,52,-1:7,52:4,125," +
"52:7,-1:2,52,-1:6,130,-1:3,52:2,130,52:6,-1,52:4,128,52:4,-1:3,52:5,-1:5,52" +
",-1:7,52:5,128,52:6,-1:2,52,-1:6,205,-1:3,52:2,205,52:6,-1,52:6,207,52:2,-1" +
":3,52:3,207,52,-1:5,52,-1:7,52:12,-1:2,52,-1:6,133,-1:3,52:2,133,52:6,-1,52" +
":4,131,52:4,-1:3,52:5,-1:5,52,-1:7,52:5,131,52:6,-1:2,52,-1:10,92:2,-1,92:6" +
",-1,92:9,-1:3,92:3,204,92,-1:5,92,-1:7,92:12,-1:13,92:2,-1,92:6,-1,92:9,-1:" +
"3,92:2,218,92:2,-1:5,92,-1:7,92:12,-1:5");

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
		case STRING:
		return new Symbol(TokenConstants.ERROR, "Se alcanzo EOF en string!");
	    case COMENT:
	   	return new Symbol(TokenConstants.ERROR, "Se alcanzo EOF en comentario!");
	   case EOF:
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
						{return new Symbol(TokenConstants.EQ,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -3:
						break;
					case 3:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -4:
						break;
					case 4:
						{}
					case -5:
						break;
					case 5:
						{white++;}
					case -6:
						break;
					case 6:
						{curr_lineno++;}
					case -7:
						break;
					case 7:
						{}
					case -8:
						break;
					case 8:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -9:
						break;
					case 9:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -10:
						break;
					case 10:
						{return new Symbol(TokenConstants.DOT,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -11:
						break;
					case 11:
						{return new Symbol(TokenConstants.AT,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -12:
						break;
					case 12:
						{return new Symbol(TokenConstants.NEG,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -13:
						break;
					case 13:
						{return new Symbol(TokenConstants.DIV,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -14:
						break;
					case 14:
						{return new Symbol(TokenConstants.MULT,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -15:
						break;
					case 15:
						{return new Symbol(TokenConstants.PLUS,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -16:
						break;
					case 16:
						{return new Symbol(TokenConstants.MINUS,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -17:
						break;
					case 17:
						{return new Symbol(TokenConstants.LT,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -18:
						break;
					case 18:
						{return new Symbol(TokenConstants.COMMA,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -19:
						break;
					case 19:
						{return new Symbol(TokenConstants.COLON,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -20:
						break;
					case 20:
						{return new Symbol(TokenConstants.SEMI,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -21:
						break;
					case 21:
						{return new Symbol(TokenConstants.LBRACE,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -22:
						break;
					case 22:
						{return new Symbol(TokenConstants.RBRACE,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -23:
						break;
					case 23:
						{return new Symbol(TokenConstants.LPAREN,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -24:
						break;
					case 24:
						{return new Symbol(TokenConstants.RPAREN,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -25:
						break;
					case 25:
						{return new Symbol(TokenConstants.INT_CONST,new IdSymbol(yytext(), yytext().length(), const_count++)); }
					case -26:
						break;
					case 26:
						{ /* Sample lexical rule for "=>" arrow.
                                     Further lexical rules should be defined
                                     here, after the last %% separator */
                                  return new Symbol(TokenConstants.DARROW); }
					case -27:
						break;
					case 27:
						{return new Symbol(TokenConstants.FI,new IdSymbol(yytext(), yytext().length(), white++));}
					case -28:
						break;
					case 28:
						{return new Symbol(TokenConstants.IF,new IdSymbol(yytext(), yytext().length(), white++));}
					case -29:
						break;
					case 29:
						{return new Symbol(TokenConstants.IN,new IdSymbol(yytext(), yytext().length(), white++));}
					case -30:
						break;
					case 30:
						{return new Symbol(TokenConstants.OF,new IdSymbol(yytext(), yytext().length(), white++));}
					case -31:
						break;
					case 31:
						{return new Symbol(TokenConstants.LE,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -32:
						break;
					case 32:
						{return new Symbol(TokenConstants.ASSIGN,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -33:
						break;
					case 33:
						{
									String token = yytext().substring(1, yytext().length() - 1);
									int l = token.length();
									return new Symbol(TokenConstants.STR_CONST, new StringSymbol(token,l,0));
								 }
					case -34:
						break;
					case 34:
						{return new Symbol(TokenConstants.LET,new IdSymbol(yytext(), yytext().length(), white++));}
					case -35:
						break;
					case 35:
						{return new Symbol(TokenConstants.NEW,new IdSymbol(yytext(), yytext().length(), white++));}
					case -36:
						break;
					case 36:
						{return new Symbol(TokenConstants.NOT,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -37:
						break;
					case 37:
						{return new Symbol(TokenConstants.ELSE,new IdSymbol(yytext(), yytext().length(), white++));}
					case -38:
						break;
					case 38:
						{return new Symbol(TokenConstants.ESAC,new IdSymbol(yytext(), yytext().length(), white++));}
					case -39:
						break;
					case 39:
						{return new Symbol(TokenConstants.POOL,new IdSymbol(yytext(), yytext().length(), white++));}
					case -40:
						break;
					case 40:
						{return new Symbol(TokenConstants.THEN,new IdSymbol(yytext(), yytext().length(), white++));}
					case -41:
						break;
					case 41:
						{return new Symbol(TokenConstants.LOOP,new IdSymbol(yytext(), yytext().length(), white++));}
					case -42:
						break;
					case 42:
						{return new Symbol(TokenConstants.CASE,new IdSymbol(yytext(), yytext().length(), white++));}
					case -43:
						break;
					case 43:
						{return new Symbol(TokenConstants.BOOL_CONST,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -44:
						break;
					case 44:
						{return new Symbol(TokenConstants.CLASS,new IdSymbol(yytext(), yytext().length(), white++));}
					case -45:
						break;
					case 45:
						{return new Symbol(TokenConstants.WHILE,new IdSymbol(yytext(), yytext().length(), white++));}
					case -46:
						break;
					case 46:
						{return new Symbol(TokenConstants.BOOL_CONST,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -47:
						break;
					case 47:
						{return new Symbol(TokenConstants.ISVOID,new IdSymbol(yytext(), yytext().length(), white++));}
					case -48:
						break;
					case 48:
						{return new Symbol(TokenConstants.INHERITS,new IdSymbol(yytext(), yytext().length(), white++));}
					case -49:
						break;
					case 50:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -50:
						break;
					case 51:
						{white++;}
					case -51:
						break;
					case 52:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -52:
						break;
					case 53:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -53:
						break;
					case 54:
						{return new Symbol(TokenConstants.FI,new IdSymbol(yytext(), yytext().length(), white++));}
					case -54:
						break;
					case 55:
						{return new Symbol(TokenConstants.IF,new IdSymbol(yytext(), yytext().length(), white++));}
					case -55:
						break;
					case 56:
						{return new Symbol(TokenConstants.IN,new IdSymbol(yytext(), yytext().length(), white++));}
					case -56:
						break;
					case 57:
						{return new Symbol(TokenConstants.OF,new IdSymbol(yytext(), yytext().length(), white++));}
					case -57:
						break;
					case 58:
						{return new Symbol(TokenConstants.LET,new IdSymbol(yytext(), yytext().length(), white++));}
					case -58:
						break;
					case 59:
						{return new Symbol(TokenConstants.NEW,new IdSymbol(yytext(), yytext().length(), white++));}
					case -59:
						break;
					case 60:
						{return new Symbol(TokenConstants.ELSE,new IdSymbol(yytext(), yytext().length(), white++));}
					case -60:
						break;
					case 61:
						{return new Symbol(TokenConstants.ESAC,new IdSymbol(yytext(), yytext().length(), white++));}
					case -61:
						break;
					case 62:
						{return new Symbol(TokenConstants.POOL,new IdSymbol(yytext(), yytext().length(), white++));}
					case -62:
						break;
					case 63:
						{return new Symbol(TokenConstants.THEN,new IdSymbol(yytext(), yytext().length(), white++));}
					case -63:
						break;
					case 64:
						{return new Symbol(TokenConstants.LOOP,new IdSymbol(yytext(), yytext().length(), white++));}
					case -64:
						break;
					case 65:
						{return new Symbol(TokenConstants.CASE,new IdSymbol(yytext(), yytext().length(), white++));}
					case -65:
						break;
					case 66:
						{return new Symbol(TokenConstants.BOOL_CONST,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -66:
						break;
					case 67:
						{return new Symbol(TokenConstants.CLASS,new IdSymbol(yytext(), yytext().length(), white++));}
					case -67:
						break;
					case 68:
						{return new Symbol(TokenConstants.WHILE,new IdSymbol(yytext(), yytext().length(), white++));}
					case -68:
						break;
					case 69:
						{return new Symbol(TokenConstants.BOOL_CONST,new IdSymbol(yytext(), yytext().length(), white++)); }
					case -69:
						break;
					case 70:
						{return new Symbol(TokenConstants.INHERITS,new IdSymbol(yytext(), yytext().length(), white++));}
					case -70:
						break;
					case 72:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -71:
						break;
					case 73:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -72:
						break;
					case 74:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -73:
						break;
					case 75:
						{return new Symbol(TokenConstants.FI,new IdSymbol(yytext(), yytext().length(), white++));}
					case -74:
						break;
					case 76:
						{return new Symbol(TokenConstants.IF,new IdSymbol(yytext(), yytext().length(), white++));}
					case -75:
						break;
					case 77:
						{return new Symbol(TokenConstants.IN,new IdSymbol(yytext(), yytext().length(), white++));}
					case -76:
						break;
					case 78:
						{return new Symbol(TokenConstants.OF,new IdSymbol(yytext(), yytext().length(), white++));}
					case -77:
						break;
					case 79:
						{return new Symbol(TokenConstants.LET,new IdSymbol(yytext(), yytext().length(), white++));}
					case -78:
						break;
					case 80:
						{return new Symbol(TokenConstants.NEW,new IdSymbol(yytext(), yytext().length(), white++));}
					case -79:
						break;
					case 81:
						{return new Symbol(TokenConstants.ELSE,new IdSymbol(yytext(), yytext().length(), white++));}
					case -80:
						break;
					case 82:
						{return new Symbol(TokenConstants.ESAC,new IdSymbol(yytext(), yytext().length(), white++));}
					case -81:
						break;
					case 83:
						{return new Symbol(TokenConstants.POOL,new IdSymbol(yytext(), yytext().length(), white++));}
					case -82:
						break;
					case 84:
						{return new Symbol(TokenConstants.THEN,new IdSymbol(yytext(), yytext().length(), white++));}
					case -83:
						break;
					case 85:
						{return new Symbol(TokenConstants.LOOP,new IdSymbol(yytext(), yytext().length(), white++));}
					case -84:
						break;
					case 86:
						{return new Symbol(TokenConstants.CASE,new IdSymbol(yytext(), yytext().length(), white++));}
					case -85:
						break;
					case 87:
						{return new Symbol(TokenConstants.CLASS,new IdSymbol(yytext(), yytext().length(), white++));}
					case -86:
						break;
					case 88:
						{return new Symbol(TokenConstants.WHILE,new IdSymbol(yytext(), yytext().length(), white++));}
					case -87:
						break;
					case 89:
						{return new Symbol(TokenConstants.INHERITS,new IdSymbol(yytext(), yytext().length(), white++));}
					case -88:
						break;
					case 91:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -89:
						break;
					case 92:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -90:
						break;
					case 93:
						{return new Symbol(TokenConstants.FI,new IdSymbol(yytext(), yytext().length(), white++));}
					case -91:
						break;
					case 94:
						{return new Symbol(TokenConstants.IF,new IdSymbol(yytext(), yytext().length(), white++));}
					case -92:
						break;
					case 95:
						{return new Symbol(TokenConstants.LET,new IdSymbol(yytext(), yytext().length(), white++));}
					case -93:
						break;
					case 96:
						{return new Symbol(TokenConstants.ELSE,new IdSymbol(yytext(), yytext().length(), white++));}
					case -94:
						break;
					case 97:
						{return new Symbol(TokenConstants.ESAC,new IdSymbol(yytext(), yytext().length(), white++));}
					case -95:
						break;
					case 98:
						{return new Symbol(TokenConstants.POOL,new IdSymbol(yytext(), yytext().length(), white++));}
					case -96:
						break;
					case 99:
						{return new Symbol(TokenConstants.THEN,new IdSymbol(yytext(), yytext().length(), white++));}
					case -97:
						break;
					case 100:
						{return new Symbol(TokenConstants.CASE,new IdSymbol(yytext(), yytext().length(), white++));}
					case -98:
						break;
					case 101:
						{return new Symbol(TokenConstants.CLASS,new IdSymbol(yytext(), yytext().length(), white++));}
					case -99:
						break;
					case 103:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -100:
						break;
					case 104:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -101:
						break;
					case 105:
						{return new Symbol(TokenConstants.FI,new IdSymbol(yytext(), yytext().length(), white++));}
					case -102:
						break;
					case 106:
						{return new Symbol(TokenConstants.LET,new IdSymbol(yytext(), yytext().length(), white++));}
					case -103:
						break;
					case 107:
						{return new Symbol(TokenConstants.ELSE,new IdSymbol(yytext(), yytext().length(), white++));}
					case -104:
						break;
					case 109:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -105:
						break;
					case 110:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -106:
						break;
					case 111:
						{return new Symbol(TokenConstants.ELSE,new IdSymbol(yytext(), yytext().length(), white++));}
					case -107:
						break;
					case 113:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -108:
						break;
					case 114:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -109:
						break;
					case 116:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -110:
						break;
					case 117:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -111:
						break;
					case 119:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -112:
						break;
					case 120:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -113:
						break;
					case 122:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -114:
						break;
					case 123:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -115:
						break;
					case 125:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -116:
						break;
					case 126:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -117:
						break;
					case 128:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -118:
						break;
					case 129:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -119:
						break;
					case 131:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -120:
						break;
					case 132:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -121:
						break;
					case 134:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -122:
						break;
					case 135:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -123:
						break;
					case 137:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -124:
						break;
					case 138:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -125:
						break;
					case 140:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -126:
						break;
					case 141:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -127:
						break;
					case 143:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -128:
						break;
					case 144:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -129:
						break;
					case 146:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -130:
						break;
					case 147:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -131:
						break;
					case 149:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -132:
						break;
					case 150:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -133:
						break;
					case 152:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -134:
						break;
					case 153:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -135:
						break;
					case 155:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -136:
						break;
					case 156:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -137:
						break;
					case 158:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -138:
						break;
					case 159:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -139:
						break;
					case 161:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -140:
						break;
					case 162:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -141:
						break;
					case 164:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -142:
						break;
					case 165:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -143:
						break;
					case 167:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -144:
						break;
					case 168:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -145:
						break;
					case 170:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -146:
						break;
					case 171:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -147:
						break;
					case 173:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -148:
						break;
					case 175:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -149:
						break;
					case 177:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -150:
						break;
					case 179:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -151:
						break;
					case 181:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -152:
						break;
					case 183:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -153:
						break;
					case 185:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -154:
						break;
					case 187:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -155:
						break;
					case 189:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -156:
						break;
					case 191:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -157:
						break;
					case 193:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -158:
						break;
					case 195:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -159:
						break;
					case 197:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -160:
						break;
					case 199:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -161:
						break;
					case 200:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -162:
						break;
					case 201:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -163:
						break;
					case 204:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -164:
						break;
					case 206:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -165:
						break;
					case 207:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -166:
						break;
					case 208:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -167:
						break;
					case 209:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -168:
						break;
					case 210:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -169:
						break;
					case 211:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -170:
						break;
					case 213:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -171:
						break;
					case 214:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -172:
						break;
					case 215:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -173:
						break;
					case 216:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -174:
						break;
					case 217:
						{return new Symbol(TokenConstants.TYPEID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -175:
						break;
					case 218:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -176:
						break;
					case 219:
						{return new Symbol(TokenConstants.OBJECTID,new IdSymbol(yytext(), yytext().length(), var_count++)); }
					case -177:
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
