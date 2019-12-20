<template>
    <div>
        <!--书籍信息表格-->
        <el-table
                :data="bookList"
                style="width: 100%">
            <el-table-column
                    sortable
                    prop="bookId"
                    label="书籍ID">
            </el-table-column>
            <el-table-column
                    prop="bookName"
                    label="书籍名称">
            </el-table-column>
            <el-table-column
                    prop="author"
                    label="作者">
            </el-table-column>
            <el-table-column
                    prop="type"
                    label="类型">
            </el-table-column>
            <el-table-column
                    prop="publisher"
                    label="出版社">
            </el-table-column>
            <el-table-column
                    sortable
                    prop="bookPrice"
                    label="价格">
            </el-table-column>
            <el-table-column
                    sortable
                    prop="amount"
                    label="库存量">
            </el-table-column>
            <el-table-column
                    label="操作">
                <template slot-scope="scope">
                    <el-button @click="updateClick(scope.row)" type="text">修改</el-button>
                    <el-button @click="deleteClick(scope.row)" type="text">移出</el-button>
                    <el-button @click="purchaseClick(scope.row)" type="text">进货</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--插入书籍Dialog-->
        <el-button type="text" @click="insertDialogVisible = true">添加新的书籍</el-button>
        <el-dialog title="插入书籍表单" :visible.sync="insertDialogVisible">
            <el-form :model="insertDialog">
                <el-form-item label="bookName">
                    <el-input v-model="insertDialog.bookName"></el-input>
                </el-form-item>
                <el-form-item label="author">
                    <el-input v-model="insertDialog.author"></el-input>
                </el-form-item>
                <el-form-item label="type">
                    <el-input v-model="insertDialog.type"></el-input>
                </el-form-item>
                <el-form-item label="publisher">
                    <el-input v-model="insertDialog.publisher"></el-input>
                </el-form-item>
                <el-form-item label="bookPrice">
                    <el-input v-model="insertDialog.bookPrice"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="insertDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="insertBook()">插 入</el-button>
            </div>
        </el-dialog>
        <!--修改书籍Dialog-->
        <el-dialog title="插入书籍表单" :visible.sync="updateDialogVisible">
            <el-form :model="updateDialog">
                <el-form-item label="bookName">
                    <el-input v-model="updateDialog.bookName"></el-input>
                </el-form-item>
                <el-form-item label="author">
                    <el-input v-model="updateDialog.author"></el-input>
                </el-form-item>
                <el-form-item label="type">
                    <el-input v-model="updateDialog.type"></el-input>
                </el-form-item>
                <el-form-item label="publisher">
                    <el-input v-model="updateDialog.publisher"></el-input>
                </el-form-item>
                <el-form-item label="bookPrice">
                    <el-input v-model="updateDialog.bookPrice"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="updateDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateCommit()">修 改</el-button>
            </div>
        </el-dialog>
        <!--书籍进货Dialog-->
        <el-dialog title='请填写进货数量' :visible.sync="purchaseDialogVisible">
            <el-form>
                <el-form-item :label=this.purchaseDialog.purchaseBookName>
                    <el-input v-model="purchaseDialog.purchaseNumber"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="purchaseDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="purchaseBook()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "BookPanel",
        data() {
            return {
                bookList: [],
                //插入模块
                insertDialogVisible: false,
                insertDialog: {
                    bookName: "",
                    author: "",
                    type: "",
                    publisher: "",
                    bookPrice: "",
                },
                //修改模块
                updateDialogVisible: false,
                updateDialog: {
                    bookId: "",
                    bookName: "",
                    author: "",
                    type: "",
                    publisher: "",
                    bookPrice: "",
                    amount: "",
                },
                purchaseDialogVisible: false,
                purchaseDialog: {
                    purchaseNumber: 0,
                    purchaseBookId: "",
                    purchaseBookName: "",
                }
            }
        },
        mounted() {
            //获取书籍信息
            this.axios.get("/books").then(response => {
                this.bookList = response.data.result;
            })
        },
        methods: {
            insertBook() {
                this.axios.post("/books", this.insertDialog).then(response => {
                    if (response.data.code == 0) {
                        this.axios.get("/books").then(response => {
                            this.bookList = response.data.result;
                        })
                        this.insertDialogVisible = false;
                        this.$message.success(response.data.message);
                    } else {
                        this.$message.error(response.data.message);
                    }
                }).catch(e => {
                    this.$message.error("插入失败,请检查字段");
                })
            },
            updateClick(book) {
                //将选中书籍信息同步到form
                this.updateDialog.bookId = book.bookId;
                this.updateDialog.bookName = book.bookName;
                this.updateDialog.author = book.author;
                this.updateDialog.type = book.type;
                this.updateDialog.publisher = book.publisher;
                this.updateDialog.bookPrice = book.bookPrice;
                this.updateDialog.amount = book.amount;
                this.updateDialogVisible = true;
            },
            updateCommit() {
                this.axios.put("/books", this.updateDialog).then(response => {
                    if (response.data.code == 0) {
                        this.axios.get("/books").then(response => {
                            this.bookList = response.data.result;
                        })
                        this.updateDialogVisible = false;
                        this.$message.success(response.data.message);
                    } else {
                        this.$message.error(response.data.message);
                    }
                }).catch(e => {
                    this.$message.error("插入失败,请检查字段");
                })
            },
            deleteClick(book) {
                /*询问是否确认交易*/
                this.$confirm('移除后将不可恢复，是否移除?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.axios.delete("/books/" + book.bookId).then(response => {
                        if (response.data.code == 0) {
                            this.axios.get("/books").then(response => {
                                this.bookList = response.data.result;
                            })
                            this.$message.success(response.data.message);
                        } else {
                            this.$message.error(response.data.message);
                        }
                    })
                });

            },
            purchaseClick(book) {
                this.purchaseDialogVisible = true;
                this.purchaseDialog.purchaseNumber = 0;
                this.purchaseDialog.purchaseBookId = book.bookId;
                this.purchaseDialog.purchaseBookName = book.bookName;
            },
            purchaseBook() {
                this.axios.patch("/books/"
                    + this.purchaseDialog.purchaseBookId
                    + "/" + this.purchaseDialog.purchaseNumber).then(response => {
                    if (response.data.code == 0) {
                        this.axios.get("/books").then(response => {
                            this.bookList = response.data.result;
                        })
                        this.purchaseDialogVisible = false;
                        this.$message.success(response.data.message);
                    } else {
                        this.$message.error(response.data.message);
                    }
                }).catch(e => {
                    this.$message.error("进货失败,请检查字段");
                })
            }

        }
    }
</script>

<style scoped>

</style>