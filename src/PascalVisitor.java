// Generated from pascal.g4 by ANTLR 4.7.2

    import java.lang.Math;
    import java.util.HashMap;
    import java.util.Scanner;
    import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link pascalParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface pascalVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link pascalParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(pascalParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(pascalParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(pascalParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclaration(pascalParser.VarDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#vNameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVNameList(pascalParser.VNameListContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#program_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram_block(pascalParser.Program_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#statement_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement_list(pascalParser.Statement_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(pascalParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatements(pascalParser.StatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#arith_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArith_expr(pascalParser.Arith_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#bool_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool_expr(pascalParser.Bool_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#if_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_block(pascalParser.If_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(pascalParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#case_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCase_statement(pascalParser.Case_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#readln}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadln(pascalParser.ReadlnContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#writeln}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteln(pascalParser.WritelnContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#spcl_math_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpcl_math_expr(pascalParser.Spcl_math_exprContext ctx);
}