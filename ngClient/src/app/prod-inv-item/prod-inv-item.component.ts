import {Component, Input, OnInit} from '@angular/core';
import {InventoryItem} from '../model/InventoryItem';
import {User} from '../model/user';
import {Product} from '../model/Product';
import {InventoryItemStatus} from '../model/InventoryItemStatus';
import {UserService} from '../services/user.service';
import {MessageService} from 'primeng';

@Component({
    selector: 'app-prod-inv-item',
    templateUrl: './prod-inv-item.component.html',
    styleUrls: ['./prod-inv-item.component.scss'],
})
export class ProdInvItemComponent implements OnInit {
    @Input() staff: User;
    @Input() product: Product;
    inventoryItems: InventoryItem[] = [];
    loading = false;
    barcode: string;

    constructor(private messageService: MessageService,
                private staffService: UserService) {
    }

    ngOnInit() {
        this.inventoryItems = this.product.inventoryItems;
    }

    onEnterPress(event) {
        if (event.key === 'Enter') {
            this.addItem();
        }
    }

    addItem() {
        if (this.inventoryItems.findIndex(val => val.barcode === this.barcode) === -1) {
            const newItem: InventoryItem = {
                barcode: this.barcode,
                addedById: this.staff?.id,
                productId: this.product.id,
                registrationDate: new Date(),
                status: InventoryItemStatus.ADDED,
            };
            this.inventoryItems.unshift(newItem);
        } else {
            this.messageService.add({severity: 'warn', summary: 'Warn Message', detail: 'The same barcode was already added.'});
        }
        this.barcode = '';
    }

    getStaffById(id: number): User {
        return this.staffService.getById(id);
    }

    removeItem(toRemove: InventoryItem) {
        if (toRemove.status === InventoryItemStatus.ADDED) {
            this.inventoryItems = this.inventoryItems.filter(item => item.barcode !== toRemove.barcode);
        } else if (toRemove.status === InventoryItemStatus.AVAILABLE) {
            toRemove.status = InventoryItemStatus.TOREMOVE;
        }
    }

    public getItems() {
        return this.inventoryItems;
    }

    getRowBg(inventoryItem: InventoryItem) {
        if (inventoryItem.status === InventoryItemStatus.ADDED) {
            return '#90ee904d';
        } else if (inventoryItem.status === InventoryItemStatus.TOREMOVE) {
            return 'indianred';
        } else if (inventoryItem.status === InventoryItemStatus.REMOVED) {
            return 'gray';
        } else {
            return '';
        }
    }
}
